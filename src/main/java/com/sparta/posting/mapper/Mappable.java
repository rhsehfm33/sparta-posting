package com.sparta.posting.mapper;


public interface Mappable<Req, E, Res> {
    public E toEntity(Req requestDto);

    public Res toDto(E entity);
}
