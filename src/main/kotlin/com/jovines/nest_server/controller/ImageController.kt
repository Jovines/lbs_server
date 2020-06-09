package com.jovines.nest_server.controller

import com.jovines.nest_server.bean.StatusWarp
import com.jovines.nest_server.config.IMG_PATH
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileInputStream
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * @author Jovines
 * create 2020-04-30 5:45 下午
 * description: 图片
 */
@RestController
@RequestMapping("image")
class ImageController {

    /**
     * 处理图片显示请求
     * @param fileName
     */
    @RequestMapping("/{fileName}.{suffix}")
    fun showPicture(@PathVariable("fileName") fileName: String,
                    @PathVariable("suffix") suffix: String,
                    response: HttpServletResponse) {
        val imgFile = File("$IMG_PATH$fileName.$suffix")
        responseFile(response, imgFile)
    }

    /**
     * 处理图片下载请求
     * @param fileName
     * @param response
     */
    @RequestMapping("/downloadImage/{fileName}.{suffix}")
    fun downloadPicture(@PathVariable("fileName") fileName: String,
                        @PathVariable("suffix") suffix: String,
                        response: HttpServletResponse) {
        // 设置下载的响应头信息
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + "headPic.jpg")
        val imgFile = File("$IMG_PATH$fileName.$suffix")
        responseFile(response, imgFile)
    }

    /**
     * 上传图片
     *
     * code:
     *       1000 上传成功
     *       1001 上传失败，文件为空
     *       1002 文件名为空
     */
    @PostMapping("/imageUpload")
    fun fileUpload(@RequestParam(value = "image") file: MultipartFile, request: HttpServletRequest): StatusWarp<String> {
        if (file.isEmpty) return StatusWarp(1001, "")
        var fileName = file.originalFilename // 文件名
        val suffixName = fileName?.substringAfterLast(".") // 后缀名
        if (suffixName == null || suffixName.isEmpty()) return StatusWarp(1002, "")
        fileName = "${UUID.randomUUID()}.$suffixName" // 新文件名
        val dest = File("$IMG_PATH$fileName")
        if (!dest.parentFile.exists()) dest.parentFile.mkdirs()
        file.transferTo(dest)
        return StatusWarp(1000, "$fileName")
    }

    /**
     * 响应输出图片文件
     * @param response
     * @param imgFile
     */
    private fun responseFile(response: HttpServletResponse, imgFile: File) {
        FileInputStream(imgFile).use { inputStream ->
            response.outputStream.use { os ->
                val buffer = ByteArray(1024) // 图片文件流缓存池
                while (inputStream.read(buffer) != -1) {
                    os.write(buffer)
                }
                os.flush()
            }
        }
    }

}