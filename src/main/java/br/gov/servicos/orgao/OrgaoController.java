package br.gov.servicos.orgao;

import br.gov.servicos.cms.Conteudo;
import br.gov.servicos.cms.Markdown;
import br.gov.servicos.servico.ServicoRepository;
import br.gov.servicos.v3.schema.Orgao;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

@Controller
@FieldDefaults(level = PRIVATE, makeFinal = true)
class OrgaoController {

    Markdown markdown;
    OrgaoRepository orgaos;
    ServicoRepository servicos;

    @Autowired
    OrgaoController(Markdown markdown, OrgaoRepository orgaos, ServicoRepository servicos) {
        this.markdown = markdown;
        this.orgaos = orgaos;
        this.servicos = servicos;
    }

    @RequestMapping("/orgaos")
    ModelAndView orgaos() {
        return new ModelAndView("orgaos", "orgaos", orgaos.findAll());
    }

    @RequestMapping("/orgao/{id}")
    ModelAndView orgao(@PathVariable String id) {
        Map<String, Object> model = new HashMap<>();

        model.put("termo", id);
        model.put("conteudo", markdown.toHtml(new ClassPathResource(format("conteudo/orgaos/%s.md", id))).withId(id));
        model.put("resultados", servicos.findByOrgao(new Orgao().withId(id))
                .stream()
                .map(Conteudo::fromServico)
                .sorted((left, right) -> left.getId().compareTo(right.getId()))
                .collect(toList()));

        return new ModelAndView("orgao", model);
    }

}
