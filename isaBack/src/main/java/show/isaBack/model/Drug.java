package show.isaBack.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

@Table(name = "Drug")
public class Drug {


    @Id
    private UUID id;

    @Column(name="name", nullable=false)
    private String name;

    @Column(name="producerName", nullable=false)
    private String producerName;


    @Column(name="fabricCode", nullable=false, unique=true)
    private String fabricCode;
    
   




    public Drug(UUID id, String name, String producerName, String fabricCode) {
        super();
        this.id = id;
        this.name = name;
        this.producerName = producerName;
        this.fabricCode = fabricCode;
    }


    public Drug(String name, String producerName, String fabricCode) {
        super();
        this.id=UUID.randomUUID();
        this.name = name;
        this.producerName = producerName;
        this.fabricCode = fabricCode;
    }


    public Drug() {}


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public String getFabricCode() {
        return fabricCode;
    }

    public void setFabricCode(String fabricCode) {
        this.fabricCode = fabricCode;
    }


}