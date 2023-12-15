package tw.niq.micro.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

import tw.niq.micro.domain.Role;
import tw.niq.micro.dto.RoleDto;
import tw.niq.micro.model.RoleModel;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface RoleMapper extends BaseMapper {
	
	RoleModel toRoleModel(RoleDto roleDto, @Context CycleAvoidingMappingContext context);
	
	RoleDto toRoleDto(RoleModel roleModel, @Context CycleAvoidingMappingContext context);
	
	RoleDto toRoleDto(Role role, @Context CycleAvoidingMappingContext context);
	
	Role toRole(RoleDto roleDto, @Context CycleAvoidingMappingContext context);
	
}
