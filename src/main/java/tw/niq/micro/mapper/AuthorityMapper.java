package tw.niq.micro.mapper;

import org.mapstruct.Mapper;

import tw.niq.micro.domain.Authority;
import tw.niq.micro.dto.AuthorityDto;
import tw.niq.micro.model.AuthorityModel;

@Mapper
public interface AuthorityMapper extends BaseMapper {

	AuthorityModel toAuthorityModel(AuthorityDto authorityDto);
	
	AuthorityDto toAuthorityDto(AuthorityModel authorityModel);
	
	AuthorityDto toAuthorityDto(Authority authority);
	
	Authority toAuthority(AuthorityDto authorityDto);
	
}
