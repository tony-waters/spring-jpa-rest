package uk.bit1.spring_jpa.service.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

//    // Entity -> DTO (flat; ignores orders automatically because DTO has no orders field)
//    ProductDto toDto(Product product);
//
//    List<ProductDto> toDtos(List<Product> products);
//
//    Set<ProductDto> toDtos(Set<Product> products);
//
//    // DTO -> Entity (also flat; does not touch relationships)
//    Product toEntity(ProductDto dto);
//
//    List<Product> toEntities(List<ProductDto> dtos);
//
//    Set<Product> toEntities(Set<ProductDto> dtos);
}
