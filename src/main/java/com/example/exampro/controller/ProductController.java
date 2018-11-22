package com.example.exampro.controller;

import com.example.exampro.dao.InStockRepo;
import com.example.exampro.dao.ProductRepo;
import com.example.exampro.dao.SupplierRepo;
import com.example.exampro.dao.UserRepo;
import com.example.exampro.models.Product;
import com.example.exampro.models.ProductInStock;
import com.example.exampro.models.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class ProductController {
    @Autowired
    ProductRepo productRepo = new ProductRepo();
    @Autowired
    InStockRepo inStockRepo = new InStockRepo();
    @Autowired
    SupplierRepo supplierRepo = new SupplierRepo();
    @Autowired
    UserRepo userRepo = new UserRepo();

    @GetMapping("/inStock")
    public String inStock(Model model) {
        if(userRepo.getLoggedInUser().getRole().equals("Admin")) {
            model.addAttribute("admin", true);
        }
        else if(userRepo.getLoggedInUser().getRole().equals("Bruger")){
            model.addAttribute("user", true);
        }
        model.addAttribute("productsInStock", inStockRepo.readAll());
        return "inStock";
    }

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("products", productRepo.readAll());
        return "products";
    }

    @GetMapping("/scanProductIn")
    public String scanProduct(Model model) {
        model.addAttribute("productInStock", new ProductInStock());
        return "scanProductIn";
    }

    @PostMapping("/scanProductIn")
    public String scanProduct(@ModelAttribute ProductInStock productInStock, Model model) {
        System.out.println(productInStock);
        if (productRepo.readBarcode(productInStock.getProduct().getBarcode()) != null) {
            productInStock.setReceiptDate(LocalDate.now());
            productInStock.setProduct(productRepo.readBarcode(productInStock.getProduct().getBarcode()));
            productInStock.setUser(userRepo.getLoggedInUser());
            model.addAttribute("productInStock", productInStock);
            model.addAttribute("product", productInStock.getProduct());
            return "createProductInStock";
        }
        model.addAttribute("productInStock", productInStock);
        model.addAttribute("suppliers", supplierRepo.readAll());
        return "createProduct";
    }

    @PostMapping("/createProduct")
    public String createProduct(@ModelAttribute ProductInStock productInStock, Model model) {
        productInStock.setUser(userRepo.getLoggedInUser());
        productInStock.setReceiptDate(LocalDate.now());
        productInStock.getProduct().setSupplier(supplierRepo.readString(productInStock.getProduct().getSupplier().getSupplier()));
        productRepo.create(productInStock.getProduct());
        model.addAttribute("productInStock", productInStock);
        return "createProductInStock";
    }

    @PostMapping("/createProductInStock")
    public String createProductInStock(@ModelAttribute ProductInStock productInStock) {
        productInStock.setProduct(productRepo.readBarcode(productInStock.getProduct().getBarcode()));
        productInStock.setUser(userRepo.getLoggedInUser());
        inStockRepo.create(productInStock);
        return "redirect:/inStock";
    }

    @GetMapping("/updateProduct")
    public String updateProduct(Model model, @RequestParam("id") int id) {
        model.addAttribute("product", productRepo.read(id));
        model.addAttribute("suppliers", supplierRepo.readAll());
        model.addAttribute("supplier", new Supplier());
        return "updateProduct";
    }

    @PostMapping("/updateProduct")
    public String updateProduct(@ModelAttribute Product product) {
        product.setSupplier(supplierRepo.readString(product.getSupplier().getSupplier()));

        productRepo.update(product);
        return "redirect:/products";
    }

    @GetMapping("/deleteProduct")
    public String deleteProduct(@RequestParam("id") int id, Model model) {
        model.addAttribute("product", productRepo.read(id));
        return "deleteProduct";
    }

    @PostMapping("/deleteProduct")
    public String deleteProduct(@ModelAttribute Product product, Model model) {
        if (inStockRepo.read(product.getId()) == null) {
            productRepo.delete(product);
            model.addAttribute("success", true);
            model.addAttribute("products", productRepo.readAll());
            return "products";
        }
        model.addAttribute("failure", true);
        model.addAttribute("products", productRepo.readAll());
        return "products";
    }

    @GetMapping("/deleteProductInStock")
    public String deleteProductInStock(@RequestParam("id") int id, Model model) {
        if(userRepo.getLoggedInUser().getRole().equals("Admin")) {
            model.addAttribute("admin", true);
        }
        else if(userRepo.getLoggedInUser().getRole().equals("Bruger")){
            model.addAttribute("user", true);
        }
        model.addAttribute("productInStock", inStockRepo.read(id));

        return "deleteProductInStock";
    }

    @PostMapping("/deleteProductInStock")
    public String deleteProductInStock(@RequestParam("id") int id, @ModelAttribute ProductInStock productInStock) {
        productInStock = inStockRepo.read(id);
        inStockRepo.delete(productInStock);
        return "redirect:/inStock";
    }

    @GetMapping("/createSupplier")
    public String createSupplier(Model model) {
        model.addAttribute("supplier", new Supplier());
        return "createSupplier";
    }

    @PostMapping("/createSupplier")
    public String createSupplier(@ModelAttribute Supplier supplier, Model model) {
        supplierRepo.create(supplier);
        model.addAttribute("productInStock", new ProductInStock());
        model.addAttribute("suppliers", supplierRepo.readAll());
        return "createProduct";
    }
    @GetMapping("/scanProductOut")
    public String scanProductOut(Model model) {
        model.addAttribute("productInStock", new ProductInStock());
        return "scanProductOut";
    }

    @PostMapping("/scanProductOut")
    public String scanProductOut(@ModelAttribute ProductInStock productInStock, Model model) {

        if (inStockRepo.checkExpirationDate(productInStock) != null) {
            ProductInStock oldestProduct = new ProductInStock();
            oldestProduct = inStockRepo.checkExpirationDate(productInStock);
            if(productInStock.getProduct().getBarcode().equals(oldestProduct.getProduct().getBarcode()) && oldestProduct.getExpirationDate().isEqual(productInStock.getExpirationDate())) {
                inStockRepo.delete(oldestProduct);
                return "redirect:/inStock";
            }
            else{
                model.addAttribute("productIsNotTheOldest", true);
                model.addAttribute("oldestProduct", oldestProduct);
                return "scanProductOut";
            }
        }

        model.addAttribute("productDoesNotExist", true);
        return "scanProductOut";
    }

    @GetMapping("/updateProductInStock")
    public String updateProductInStock(@RequestParam("id") int id, Model model) {
        if(userRepo.getLoggedInUser().getRole().equals("Admin")) {
            model.addAttribute("admin", true);
        }
        else if(userRepo.getLoggedInUser().getRole().equals("Bruger")){
            model.addAttribute("user", true);
        }
        model.addAttribute("productInStock", inStockRepo.read(id));
        return "updateProductInStock";
    }

    @PostMapping("/updateProductInStock")
    public String updateProductInStock(@RequestParam("id") int id, @ModelAttribute ProductInStock productInStock) {
        productInStock.setId(id);
        inStockRepo.update(productInStock);
        return "redirect:/inStock";
    }
}
