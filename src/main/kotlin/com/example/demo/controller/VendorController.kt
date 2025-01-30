package com.example.demo.controller

import com.example.demo.entity.Vendor
import com.example.demo.repository.VendorRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/admin/vendor")
class VendorController(private val vendorRepository: VendorRepository) {

    @GetMapping
    fun listVendors(model: Model): String {
        val vendors = vendorRepository.findAll()
        model.addAttribute("vendors", vendors)
        return "vendor_list"
    }

    @GetMapping("/add")
    fun showAddVendorForm(model: Model): String {
        model.addAttribute("vendor", Vendor())
        return "vendor_form"
    }

    @PostMapping("/add")
    fun addVendor(@ModelAttribute vendor: Vendor, model: Model): String {
        return try {
            vendorRepository.save(vendor)
            "redirect:/admin/vendor"
        } catch (e: DataIntegrityViolationException) {
            model.addAttribute("errorMessage", "Nelze přidat dodavatele, protože dodavatel s tímto ID již existuje.")
            "vendor_error"
        }
    }

    @GetMapping("/edit/{id}")
    fun showEditVendorForm(@PathVariable id: Long, model: Model): String {
        val vendor = vendorRepository.findById(id).orElseThrow()
        model.addAttribute("vendor", vendor)
        return "vendor_form"
    }

    @PostMapping("/edit/{id}")
    fun editVendor(@PathVariable id: Long, @ModelAttribute vendor: Vendor, model: Model): String {
        return try {
            vendorRepository.save(vendor)
            "redirect:/admin/vendor"
        } catch (e: DataIntegrityViolationException) {
            model.addAttribute("errorMessage", "Nelze upravit dodavatele, protože dodavatel s tímto ID již existuje.")
            "vendor_error"
        }
    }



    @PostMapping("/delete/{id}")
    fun deleteVendor(@PathVariable id: Long, model: Model): String {
        return try {
            vendorRepository.deleteById(id)
            "redirect:/admin/vendor"
        } catch (e: DataIntegrityViolationException) {
            model.addAttribute("errorMessage", "Nelze smazat dodavatele, protože již dodává jídlo.")
            "vendor_error"
        }
    }
}
