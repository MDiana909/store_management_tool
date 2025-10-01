package com.dvm.store_management_tool.product_service.dto.product;

import java.math.BigDecimal;
import java.util.Optional;

public record ProductUpdateRequest (Optional<String> name, Optional<BigDecimal> price, Optional<Integer> stock) {
}
