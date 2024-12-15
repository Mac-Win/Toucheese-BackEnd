package com.toucheese.cart.dto;

import java.util.List;

public record CartUpdateRequest (
	Integer totalPrice,
	Integer personnel,
	List<Long> addOptions
){

}
