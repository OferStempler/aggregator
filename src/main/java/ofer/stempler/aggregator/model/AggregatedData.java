package ofer.stempler.aggregator.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Entity
@Table( indexes = {@Index(name = "provider_index",  columnList="provider"),
        @Index(name = "error_code_index", columnList="created_error_code" ),
        @Index(name = "status_index", columnList="status" )
})
public class AggregatedData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @JsonProperty("Case ID")
    @Column(name = "case_id")
    private int caseId;

    @JsonProperty("Customer ID")
    @Column(name = "customer_id")
    private int customerId;

    @JsonProperty("Provider")
    @Column(name = "provider")
    private int provider;

    @JsonProperty("CREATED_ERROR_CODE")
    @Column(name = "created_error_code")
    private int createdErrorCode;

    @JsonProperty("STATUS")
    @Column(name = "status")
    private String status;

    @JsonProperty("TICKET_CREATION_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm")
    @Column(name = "ticket_creation_date")
    private Date ticketCreationDate;

    @JsonProperty("LAST_MODIFIED_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm")
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    @JsonProperty("PRODUCT_NAME")
    @Column(name = "product_name")
    private String productName;

}
