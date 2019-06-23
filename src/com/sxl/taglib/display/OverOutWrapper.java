package com.sxl.taglib.display;

import org.displaytag.decorator.TableDecorator;


public class OverOutWrapper extends TableDecorator {   
    public OverOutWrapper(){   
        super();   
    }   
    @Override  
    public String addRowId() {   
        return "i_d\" onmouseover=\"if (typeof(window.m_over)=='function') window.m_over(this);\" onmouseout=\"if (typeof(window.m_out)=='function') window.m_out(this);\" ";   
    }   
}  
