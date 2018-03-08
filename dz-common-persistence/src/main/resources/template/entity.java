package $

import lombok.EqualsAndHashCode;{basePackage}.model.entity;

import com.dazong.persistence.annotation.TableField;
import com.dazong.persistence.annotation.TableId;
import com.dazong.persistence.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <B>说明：${entity.comment}</B><BR>
 *
 * @author ${author}
 * @version 1.0.0.
 * @date ${date}
 */
@TableName("${tableName}")
@Data
@EqualsAndHashCode(callSuper = false)
public class ${upperModelName} implements Serializable {

    private static final long serialVersionUID = 1L;
<% for(var item in items) {%>
    /**
     * ${item.comment}
     */
    <% if (item.column == entity.keyColumn) {%>
    @TableId
    <% } else {%>
    @TableField("${item.column}")
    <% }%>
    private ${item.propertyType} ${item.property};
<% }%>


}
