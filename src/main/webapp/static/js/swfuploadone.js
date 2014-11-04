var swfu;
        $(function(){
            swfu = new SWFUpload({
                upload_url: "/ajaxUpload.htm",
                file_post_name: "files", 
                post_params: {"accept" : "file"},
                use_query_string:false,
                // File Upload Settings
                file_size_limit : "10 MB",  // 文件大小控制
                file_types : "*.jpg;*.gif,*.png",
                file_types_description : "All Files",
                file_upload_limit : "0",
                file_queue_error_handler : fileQueueError,
                file_dialog_complete_handler : fileDialogComplete,//选择好文件后提交
                file_queued_handler : uploadComplete,//选择好文件后
                upload_progress_handler : uploadProgress,//等待上传
                upload_error_handler : uploadError,
                upload_success_handler : uploadSuccess,
                upload_complete_handler : uploadComplete,
                button_image_url: "/static/js/swfupload/button.png",
                button_placeholder_id : "spanButtonPlaceholder",
                button_width: 140,
                button_height: 35,
                button_action:SWFUpload.BUTTON_ACTION.SELECT_FILE,
                button_text_top_padding: 0,
                button_text_left_padding: 18,
                button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
                button_cursor: SWFUpload.CURSOR.HAND,
                // Flash Settings
                flash_url : "/static/js/swfupload/flash/swfupload.swf",
                custom_settings : {
                    upload_target : "divFileProgressContainer"
                },
                // Debug Settings
                debug: false  //是否显示调试窗口
            });
        }
		);
        function startUploadFile(){
            swfu.startUpload();
        }
