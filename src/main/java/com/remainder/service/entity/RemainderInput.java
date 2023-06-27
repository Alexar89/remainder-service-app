package com.remainder.service.entity;

import org.springframework.lang.NonNull;

public class RemainderInput {
	@NonNull
    private Integer x;
	@NonNull
    private Integer y;
	@NonNull
    private Integer n;

    // Constructor por defecto
    public RemainderInput() {
    }

    // Constructor basado en propiedades
    public RemainderInput(Integer x, Integer y, Integer n) {
        this.x = x;
        this.y = y;
        this.n = n;
    }

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public Integer getN() {
		return n;
	}

	public void setN(Integer n) {
		this.n = n;
	}

    
}
