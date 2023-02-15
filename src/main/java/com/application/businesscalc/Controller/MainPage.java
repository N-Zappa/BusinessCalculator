package com.application.businesscalc.Controller;

import com.application.businesscalc.Model.Item;
import com.application.businesscalc.Repository.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
public class MainPage {
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static";
    @Autowired
    private ItemRepo itemRepo;
    @GetMapping("/business/calc")
    public String GetMainPage(Model model)
    {
        Iterable<Item> items = itemRepo.findAll();
        model.addAttribute("items", items);
        return "MainPage";
    }
    @GetMapping("business/calc/add")
    public String AddItem(Model model, Item item)
    {
        model.addAttribute("item", item);
        return "AddForm";
    }
    @PostMapping("business/calc/add")
    public String PostItem(Item item,
                           @RequestParam(value = "buyprice") double buyprice,
                           @RequestParam(value = "sellprice") double sellprice,
                           @RequestParam("image") MultipartFile file) throws IOException {
        item.setBuyPrice(buyprice);
        item.setSellPrice(sellprice);
        item.setIncome(item.getSellPrice()-item.getBuyPrice());
        String percentOfIncome = new DecimalFormat("#0.00").format((item.getIncome()/item.getBuyPrice())*100);
        item.setPercentIncome(percentOfIncome + "%");
        if(item.getSellPrice()!=0) item.setStatus("Sold");
        else item.setStatus("Not sold yet");
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        item.setDate(simpleDateFormat.format(date).toString());
        StringBuilder fileNames = new StringBuilder();
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        fileNames.append(file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        item.setImageName(fileNames.toString());
        itemRepo.save(item);
        return "redirect:/business/calc";
    }
    @GetMapping("business/calc/statistic")
    public String StatistivOfAllTime(Model model)
    {
        int BuyCounter = 0; int SellCounter = 0; int IncomeCounter = 0;
        Iterable<Item> items = itemRepo.findAll();
        for (Item item : items)
        {
            BuyCounter+=item.getBuyPrice();
            SellCounter+=item.getSellPrice();
            IncomeCounter+=item.getIncome();
        }
        model.addAttribute("BuyCounter", BuyCounter);
        String DollarBuy = new DecimalFormat("#0.00").format(BuyCounter/2.76);
        model.addAttribute("DollarBuy", DollarBuy);
        model.addAttribute("SellCounter", SellCounter);
        String DollarSell = new DecimalFormat("#0.00").format(SellCounter/2.76);
        model.addAttribute("DollarSell", DollarSell);
        model.addAttribute("IncomeCounter", IncomeCounter);
        String DollarIncome = new DecimalFormat("#0.00").format(IncomeCounter/2.76);
        model.addAttribute("DollarIncome", DollarIncome);
        return "Statistics";
    }
    @GetMapping("business/calc/{id}/edit")
    public String EditItem(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Item> item = itemRepo.findById(id);
        model.addAttribute("item", item);
        return "EditItem";
    }
    @PostMapping("business/calc/{id}/edit")
    public String EditItemPost(@PathVariable(value = "id") long id,
                               @RequestParam(value = "name") String name,
                               @RequestParam(value = "description") String description,
                               @RequestParam(value = "buyprice") double buyprice,
                               @RequestParam(value = "sellprice") double sellprice)
    {
        Optional<Item> item = itemRepo.findById(id);
        item.get().setName(name);
        item.get().setDescription(description);
        item.get().setBuyPrice(buyprice);
        item.get().setSellPrice(sellprice);
        itemRepo.save(item.get());
        return "redirect:/business/calc";
    }
    @PostMapping("business/calc/{id}/delete")
    public String DeleteItem(@PathVariable(value = "id") long id)
    {
        Item deleteItem = itemRepo.findById(id).get();
        File file = new File(UPLOAD_DIRECTORY + "/" + deleteItem.getImageName());
        file.delete();
        itemRepo.delete(deleteItem);
        return "redirect:/business/calc";
    }
}
