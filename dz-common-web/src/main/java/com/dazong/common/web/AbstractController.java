package com.dazong.common.web;

import org.springframework.web.servlet.ModelAndView;
import com.dazong.common.AbstractBase;

/**
 * 页面基础类
 */
public abstract class AbstractController extends AbstractBase {

    /**
     * 渲染页面
     *
     * @return
     */
    public ModelAndView render() {
        return new ModelAndView();
    }

    /**
     * 根据page名称，渲染页面
     *
     * @param page
     * @return
     */
    public ModelAndView render(String page) {
        ModelAndView mv = render();
        mv.setViewName(page);
        return mv;
    }

}
