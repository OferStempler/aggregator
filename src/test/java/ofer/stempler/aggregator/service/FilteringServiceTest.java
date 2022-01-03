package ofer.stempler.aggregator.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ofer.stempler.aggregator.dao.AggregatedDataDao;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class FilteringServiceTest {

    @Autowired
    private AggregatedDataDao aggregatedDataDao;

    @Test
    public void testSpecificationFilter() {
        assertThat(aggregatedDataDao.findAll().size()).isEqualTo(3);

        assertThat(aggregatedDataDao.findAll(Specification.where(AggregatorDataSpecifications.withId())).size()).isEqualTo(3);

        assertThat(aggregatedDataDao.findAll(Specification.where(
                AggregatorDataSpecifications.withId().and(AggregatorDataSpecifications.withStatus("Open"))))
                .size()).isEqualTo(2);

        assertThat(aggregatedDataDao.findAll(Specification.where(
                AggregatorDataSpecifications.withId()
                        .and(AggregatorDataSpecifications.withStatus("Open"))
                        .and(AggregatorDataSpecifications.withProvider(1)
                                .and(AggregatorDataSpecifications.withErrorCode(1)))))
                .size()).isEqualTo(1);
    }
}
