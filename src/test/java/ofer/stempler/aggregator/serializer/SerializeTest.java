package ofer.stempler.aggregator.serializer;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ofer.stempler.aggregator.model.Aggregator;
import ofer.stempler.aggregator.utils.TestUtils;

@ExtendWith(SpringExtension.class)
public class SerializeTest {

    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");

    @Test
    public void testModelSerialize() throws IOException, ParseException {

        Aggregator banana = TestUtils.createInstanceFromJson("crm1.json", Aggregator.class);

        assertThat(banana.getAggregatedDataList().size()).isEqualTo(3);
        assertThat(banana.getAggregatedDataList().get(0).getCaseId()).isEqualTo(1);
        assertThat(banana.getAggregatedDataList().get(0).getCustomerId()).isEqualTo(818591);
        assertThat(banana.getAggregatedDataList().get(0).getProvider()).isEqualTo(6111);
        assertThat( banana.getAggregatedDataList().get(0).getProductName()).isEqualTo("BLUE");

        assertThat(format.parse("3/14/2019 16:30").equals(banana.getAggregatedDataList().get(0).getTicketCreationDate()));
        assertThat(format.parse("3/17/2019 3:41").equals(banana.getAggregatedDataList().get(0).getLastModifiedDate()));

    }
}
