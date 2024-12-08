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

    // Formulář
    @GetMapping("/upload")
    fun showUploadForm(): String {
        return "upload_form"
    }

    // Nahrání souboru
    @PostMapping("/upload")
    fun uploadFile(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("uploadedBy") uploadedBy: String,
        model: Model
    ): String {
        try {
            val fileId = fileService.uploadFile(file, uploadedBy) // Nahrávání souboru
            model.addAttribute("message", "Soubor byl úspěšně nahrán!")
            model.addAttribute("fileId", fileId)
        } catch (e: IOException) {
            model.addAttribute("message", "Chyba při nahrávání souboru: ${e.message}")
        }
        return "upload_result"
    }

    // Zobrazení souboru
    @GetMapping("/view/{fileId}")
    @ResponseBody
    fun viewFile(@PathVariable fileId: Long): ByteArray {
        return fileService.getFileData(fileId) // Získání dat souboru
    }
}
