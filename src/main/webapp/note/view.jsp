
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="col-md-9">
    <div class="data_list">
        <div class="data_list_title">
            <span class="glyphicon glyphicon-cloud-upload">
            </span>&nbsp;
            <c:if test="${empty noteInfo}">
                发布云记
            </c:if>
            <c:if test="${!empty noteInfo}">
                修改云记
            </c:if>
        </div>
        <div class="container-fluid">
            <div class="container-fluid">
                <div class="row" style="padding-top: 20px;">
                    <div class="col-md-12">
                        <%-- 判断类型列表是否为空 若为空 提示用户先添加类型 --%>
                        <c:if test="${empty typeList}">
                            <h2>暂未查询到云记类型！</h2>
                            <h4><a href="type?actionName=list">添加类型</a> </h4>
                        </c:if>
                        <c:if test="${!empty typeList}">
                            <form class="form-horizontal" method="post" action="note">
                                    <%-- 设置隐藏域：存放用户行为--%>
                                <input type="hidden" name="actionName" value="addOrUpdate">
                                    <%-- 设置隐藏域：用来存放noteId--%>
                                <input type="hidden" name="noteId" value="${noteInfo.noteId}">
                                    <%-- 设置隐藏域：用来存放用户发布云记时所在地区的经纬度 --%>
                                    <%-- 经度 --%>
                                <input type="hidden" name="lon" id="lon">
                                    <%-- 纬度 --%>
                                <input type="hidden" name="lat" id="lat">
                                <div class="form-group">
                                    <label for="typeId" class="col-sm-2 control-label">类别:</label>
                                    <div class="col-sm-8">
                                        <select id="typeId" class="form-control" name="typeId">
                                            <option value="">请选择云记类别...</option>
                                            <c:forEach var="item" items="${typeList}">
                                                <c:choose>
                                                    <c:when test="${!empty resultInfo}">
                                                        <option <c:if test="${resultInfo.result.typeId == item.typeId}">selected</c:if> value="${item.typeId}">${item.typeName}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option <c:if test="${noteInfo.typeId == item.typeId}">selected</c:if> value="${item.typeId}">${item.typeName}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="title" class="col-sm-2 control-label">标题:</label>
                                    <div class="col-sm-8">
                                        <c:choose>
                                            <c:when test="${!empty resultInfo}">
                                                <input class="form-control" name="title" id="title" placeholder="云记标题" value="${resultInfo.result.title}">
                                            </c:when>
                                            <c:otherwise>
                                                <input class="form-control" name="title" id="title" placeholder="云记标题" value="${noteInfo.title}">
                                            </c:otherwise>
                                        </c:choose>

                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="title" class="col-sm-2 control-label">内容:</label>
                                    <div class="col-sm-8">
                                        <c:choose>
                                            <c:when test="${!empty resultInfo}">
                                                <%-- 准备容器，加载富文本编辑器--%>
                                                <textarea id="content" name="content">${resultInfo.result.content}</textarea>
                                            </c:when>
                                            <c:otherwise>
                                                <%-- 准备容器，加载富文本编辑器--%>
                                                <%--<textarea id="content" name="content">${noteInfo.content}</textarea>--%>
                                                <div style="border: 1px solid #ccc">
                                                    <div id="toolbar-container"></div>
                                                    <div id="editor-container"></div>
                                                </div>

                                            </c:otherwise>
                                        </c:choose>

                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-4 col-sm-4">
                                        <input type="submit" class="btn btn-primary" onclick="return checkForm()" value="保存">
                                        &nbsp;<span id="msg" style="font-size: 12px;color: red">${resultInfo.msg}</span>
                                    </div>
                                </div>
                            </form>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        // var ue;
        // $(function () {
        //     // 加载富文本编辑器 UE.getEditor('容器ID')
        //     ue = UE.getEditor('content');
        // });
        const { createEditor, createToolbar, } = window.wangEditor

        const editorConfig = { MENU_CONF: {},
            excludeKeys: [
                'fullScreen',
                'insertVideo',
            ],
        } // 初始化 MENU_CONF 属性
        // var E = window.wangEditor; // 全局变量
        // console.log

         editorConfig.MENU_CONF['uploadImage'] = {
             // 上传图片的配置
             server: '${pageContext.request.contextPath}/note?actionName=upload',

             // form-data fieldName ，默认值 'wangeditor-uploaded-image'
             fieldName: 'your-custom-name',

             // 单个文件的最大体积限制，默认为 2M
             maxFileSize: 1 * 1024 * 1024, // 1M

             // 最多可上传几个文件，默认为 100
             maxNumberOfFiles: 10,

             // 选择文件时的类型限制，默认为 ['image/*'] 。如不想限制，则设置为 []
             allowedFileTypes: ['image/*'],

             // 自定义上传参数，例如传递验证的 token 等。参数会被添加到 formData 中，一起上传到服务端。
             meta: {
                 token: 'xxx',
                 otherKey: 'yyy'
             },

             // 将 meta 拼接到 url 参数中，默认 false
             metaWithUrl: false,

             // 自定义增加 http  header
             headers: {
                 Accept: 'text/x-json',
                 otherKey: 'xxx'
             },

             // 跨域是否传递 cookie ，默认为 false
             withCredentials: true,

             // 超时时间，默认为 10 秒
             timeout: 5 * 1000, // 5 秒

             // 上传之前触发
             onBeforeUpload(file) {
                 // file 选中的文件，格式如 { key: file }
                 console.log(file)
                 return file

                 // 可以 return
                 // 1. return file 或者 new 一个 file ，接下来将上传
                 // 2. return false ，不上传这个 file
             },
             // 上传进度的回调函数
             onProgress() {
                 // progress 是 0-100 的数字
                 console.log('progress', progress)
             },
             // 单个文件上传成功之后
             onSuccess() {
                 console.log(`${file.name} 上传成功`, res)
             },
             // 单个文件上传失败
             onFailed() {
                 console.log(`${file.name} 上传失败`, res)
             },
             // 上传错误，或者触发 timeout 超时
             onError() {
                 console.log(`${file.name} 上传出错`, err, res)
             },
        }
        editorConfig.placeholder = '请输入内容'
        editorConfig.onChange = () => {
            // 当编辑器选区、内容变化时，即触发
            console.log('content', editor.children)
            console.log('html', editor.getHtml())
        }

        // 创建编辑器
        const editor = createEditor({
            selector: '#editor-container',
            config: editorConfig,
            mode: 'simple', // 或 'simple' 参考下文
        })

        // 创建工具栏
        const toolbar = createToolbar({
            editor,
            selector: '#toolbar-container',
            mode: 'simple' // 或 'simple' 参考下文
        })




        /**
         * 表单校验
         1. 获取表单元素的值
         获取下拉框选中的选项 .val()
         获取文本框的值 .val()
         获取富文本编辑器的内容 ue.getContent()
         2. 参数的非空判断
         如果为空，提示用户，并return fasle
         3. 如果参数不为空，则return true，提交表单
         * @returns {boolean}
         */
        function checkForm() {
            /* 1. 获取表单元素的值 */
            // 获取下拉框选中的选项 .val()
            var typeId = $("#typeId").val();
            // 获取文本框的值 .val()
            var title = $("#title").val();
            // 获取富文本编辑器的内容 ue.getContent()
            var content = ue.getContent();
            // 2. 参数的非空判断
            if (isEmpty(typeId)) {
                $("#msg").html("请选择云记类型！");
                return false;
            }
            if (isEmpty(title)) {
                $("#msg").html("云记标题不能为空！");
                return false;
            }
            if (isEmpty(content)) {
                $("#msg").html("云记内容不能为空！");
                return false;
            }
            // 3. 如果参数不为空，则return true，提交表单
            return true;
        }
    </script>


    <%-- 引用百度地图API文件，需要申请百度地图对应ak密钥--%>
    <%--<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=yrxymYTyuefnxNtXbZcMU8phABXtu6TG"></script>--%>
    <%--<script type="text/javascript">--%>
    <%--    /* 百度地图获取当前位置经纬度 */--%>
    <%--    var geolocation = new BMap.Geolocation();--%>
    <%--    geolocation.getCurrentPosition(function (r) {--%>
    <%--        // 判断是否获取到--%>
    <%--        if (this.getStatus() == BMAP_STATUS_SUCCESS) {--%>
    <%--            console.log("您的位置:" + r.point.lng + "," + r.point.lat);--%>
    <%--            // 将获取到坐标设置给隐藏域--%>
    <%--            $("#lon").val(r.point.lng);--%>
    <%--            $("#lat").val(r.point.lat);--%>

    <%--        } else {--%>
    <%--            console.log("failed:" + thi.getStatus());--%>
    <%--        }--%>
    <%--    });--%>
    <%--</script>--%>


