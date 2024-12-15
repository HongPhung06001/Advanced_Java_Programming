package com.admission.mapper;

import com.admission.dto.BlockDTO;
import com.admission.model.Block;
import java.util.LinkedList;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface BlockMapper {

    @Mapping(target = "subjects", ignore = true)
    BlockDTO toBlockDTO(Block block);

    List<BlockDTO> toBlockDTOs(List<Block> block);

    @Mapping(target = "subjects", ignore = true)
    Block toBlock(BlockDTO blockDTO);

    @Mapping(target = "subjects", ignore = true)
    void updateBlockFromDTO(BlockDTO blockDTO, @MappingTarget Block block);

    default BlockDTO objectToBlockDto(Object[] object) {
        Integer id = (Integer) object[0];
        String code = (String) object[1];
        String subjects = (String) object[2];
        return new BlockDTO(id, code, subjects);
    }

    default List<BlockDTO> objectToBlockDtos(List<Object[]> objects) {
        List<BlockDTO> results = new LinkedList<>();
        objects.forEach(object -> {
            results.add(objectToBlockDto(object));
        });
        return results;
    }

}
