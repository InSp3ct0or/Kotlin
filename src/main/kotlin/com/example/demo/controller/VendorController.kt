package com.example.demo.controller

import com.example.demo.entity.Vendor
import com.example.demo.repository.VendorRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/vendor")
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
    fun addVendor(@ModelAttribute vendor: Vendor): String {
        vendorRepository.save(vendor)
        return "redirect:/vendor"
    }

    @GetMapping("/edit/{id}")
    fun showEditVendorForm(@PathVariable id: Long, model: Model): String {
        val vendor = vendorRepository.findById(id).orElseThrow()
        model.addAttribute("vendor", vendor)
        return "vendor_form"
    }

    @PostMapping("/edit/{id}")
    fun editVendor(@PathVariable id: Long, @ModelAttribute vendor: Vendor): String {
        vendorRepository.save(vendor)
        return "redirect:/vendor"
    }

    @PostMapping("/delete/{id}")
    fun deleteVendor(@PathVariable id: Long): String {
        vendorRepository.deleteById(id)
        return "redirect:/vendor"
    }
}
