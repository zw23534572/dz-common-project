package ${basePackage}.controller;

import ${basePackage}.common.AbstractController;
import ${basePackage}.common.pojo.QueryVo;
import ${basePackage}.common.pojo.ResultResponse;
import ${basePackage}.model.entity.${upperModelName};
import org.springframework.web.bind.annotation.GetMapping;

import ${basePackage}.service.${upperModelName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ZhouWei on 2017/08/05.
 */
@RestController
@RequestMapping("${baseRequestMapping}")
public class ${upperModelName}Controller extends AbstractController {

    @Autowired
    private ${upperModelName}Service ${lowerModelName}Service;

    /**
     * 根据条件,查询对象.支持分页
     *
     * @param queryVo
     * @return
     */
    @GetMapping("list")
    public ResultResponse listPost(QueryVo queryVo) {
        return renderJson(${lowerModelName}Service.selectList(new ${upperModelName}()));
    }

    /**
     * post请求 insert
     *
     * @param ${lowerModelName}
     * @return
     */
    @GetMapping("insert")
    public ResultResponse insert(${upperModelName} ${lowerModelName}) {
        return renderJson(${lowerModelName}Service.insert(${lowerModelName}));
    }

    /**
     * post请求 update
     *
     * @param ${lowerModelName}
     * @return
     */
    @GetMapping("update")
    public ResultResponse update(${upperModelName} ${lowerModelName}) {
        return renderJson(${lowerModelName}Service.updateById(${lowerModelName}));
    }

    /**
     * post请求 delete
     *
     * @param id 主键
     * @return
     */
    @GetMapping("delete")
    public ResultResponse delete(@RequestParam Long id) {
        return renderJson(${lowerModelName}Service.deleteById(id));
    }

    /**
     * post请求 查询单个对象
     *
     * @param id
     * @return
     */
    @GetMapping("info")
    public ResultResponse infoPost(@RequestParam Long id) {
        return renderJson(${lowerModelName}Service.selectById(id));
    }
}
