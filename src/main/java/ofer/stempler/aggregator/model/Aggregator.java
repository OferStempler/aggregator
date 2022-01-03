package ofer.stempler.aggregator.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Aggregator {

    @JsonProperty("data")
    List<AggregatedData> aggregatedDataList;

}
