package ofer.stempler.aggregator.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import ofer.stempler.aggregator.model.AggregatedData;

public class AggregatorDataSpecifications {


    public static Specification<AggregatedData> withId() {
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("id")));
    }

    public static Specification<AggregatedData> withProvider(Integer provider) {
        if (provider == null) {
            return null;
        } else {
            return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("provider"), provider));
        }
    }

    public static Specification<AggregatedData> withErrorCode(Integer createdErrorCode) {
        if (createdErrorCode == null) {
            return null;
        } else {
            return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("createdErrorCode"), createdErrorCode));
        }
    }

    public static Specification<AggregatedData> withStatus(String status) {
        if (StringUtils.isEmpty(status)) {
            return null;
        } else {
            return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status));
        }
    }
}
