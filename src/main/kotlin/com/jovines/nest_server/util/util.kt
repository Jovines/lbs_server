package com.jovines.nest_server.util

import com.jovines.nest_server.config.IMG_PATH
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


/**
 * @author Jovines
 * create 2020-05-03 9:51 下午
 * description:
 */

val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")


fun savePicture(file: MultipartFile): String {
    if (file.isEmpty) return ""
    var fileName = file.originalFilename // 文件名
    val suffixName = fileName?.substringAfterLast(".") // 后缀名
    if (suffixName == null || suffixName.isEmpty()) return ""
    fileName = "${UUID.randomUUID()}.$suffixName" // 新文件名
    val dest = File("$IMG_PATH$fileName")
    if (!dest.parentFile.exists()) dest.parentFile.mkdirs()
    file.transferTo(dest)
    return fileName
}