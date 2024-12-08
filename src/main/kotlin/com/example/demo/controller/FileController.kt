package com.example.demo.controller

import com.example.demo.service.FileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

@Controller
@RequestMapping("/files")
class FileController(@Autowired val fileService: FileService) {

    // Отображение формы для загрузки файла
    @GetMapping("/upload")
    fun showUploadForm(): String {
        return "upload_form"
    }

    // Обработка загрузки файла
    @PostMapping("/upload")
    fun uploadFile(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("uploadedBy") uploadedBy: String,
        model: Model
    ): String {
        try {
            val fileId = fileService.uploadFile(file, uploadedBy) // Загружаем файл
            model.addAttribute("message", "Файл успешно загружен!")
            model.addAttribute("fileId", fileId)
        } catch (e: IOException) {
            model.addAttribute("message", "Ошибка загрузки файла: ${e.message}")
        }
        return "upload_result"
    }

    // Просмотр файла по ID
    @GetMapping("/view/{fileId}")
    @ResponseBody
    fun viewFile(@PathVariable fileId: Long): ByteArray {
        return fileService.getFileData(fileId) // Извлекаем бинарные данные и возвращаем их
    }
}
