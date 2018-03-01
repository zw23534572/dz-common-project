package ${basePackage}.service.impl;

import ${basePackage}.common.AbstractServiceImpl;
import ${basePackage}.dao.${upperModelName}Mapper;
import ${basePackage}.model.entity.${upperModelName};
import ${basePackage}.service.${upperModelName}Service;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ${author} on ${date}.
 */
@Service
public class ${upperModelName}ServiceImpl extends AbstractServiceImpl<${upperModelName}Mapper,${upperModelName}> implements ${upperModelName}Service {
    @Autowired
    private ${upperModelName}Mapper ${lowerModelName}Mapper;

}