package br.gov.servicos.v3.schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

import static javax.xml.bind.annotation.XmlAccessType.NONE;
import static lombok.AccessLevel.PRIVATE;

@Data
@Wither
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@XmlAccessorType(NONE)
@XmlType(name = "Documentos", propOrder = {"padrao", "casos"})
public class Documentos {

    @XmlElement(name = "default", required = true)
    CasoDeDocumentos padrao;

    @XmlElement(name = "caso")
    List<CasoDeDocumentos> casos;

    public boolean isEmpty() {
        return (casos == null || casos.isEmpty()) &&
                (padrao == null || padrao.getItens() == null || padrao.getItens().isEmpty());
    }

}
