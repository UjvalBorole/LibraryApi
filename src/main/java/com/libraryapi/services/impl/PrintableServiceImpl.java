package com.libraryapi.services.impl;

import java.util.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import com.libraryapi.entities.Orders;
import com.libraryapi.entities.Printable;
import com.libraryapi.entities.User;
import com.libraryapi.exceptions.ResourceNotFoundException;
import com.libraryapi.payloads.OrderDto;
import com.libraryapi.payloads.PrintableDto;
import com.libraryapi.repository.PrintableRepo;
import com.libraryapi.services.PrintableService;
@Service
public class PrintableServiceImpl implements PrintableService{
    @Autowired
    private PrintableRepo printableRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private BookServiceImpl bookServiceImpl;

    Printable fetchPrintable(Integer printId){
        return this.printableRepo.findById(printId)
                .orElseThrow(() -> new ResourceNotFoundException("printable", "printId", printId));

    }

    @Override
    public PrintableDto createPrintable(Integer userId, Integer bookId, PrintableDto printableDto) {
        Printable printable = this.modelMapper.map(printableDto, Printable.class);
        printable.setBook(this.bookServiceImpl.fetchBook(bookId));
        printable.setUser(this.userServiceImpl.fetchUser(userId));
        printable.setPrintOrderdate(new Date());
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, 5);
        Date newDate = calendar.getTime();
        printable.setExpectedDate(newDate);
        printable.setIsdelivered(false);
        Printable print = this.printableRepo.save(printable);
        return this.modelMapper.map(print, PrintableDto.class);
    }

    @Override
    public PrintableDto updatePrintable(Integer printableId, PrintableDto printableDto) {
        Printable printable = this.fetchPrintable(printableId);
        printable.setAddress(printableDto.getAddress());
        printable.setCity(printableDto.getCity());
        printable.setPhoneNumber(printableDto.getPhoneNumber());
        printable.setStateContry(printableDto.getStateContry());
        printable.setPincode(printableDto.getPincode());
       
       Printable printable2 =  this.printableRepo.save(printable);
       return this.modelMapper.map(printable2, PrintableDto.class);
    }

    @Override
    public PrintableDto updatePrintableByAdmin(Integer printableId, PrintableDto printableDto) {
        Printable printable = this.fetchPrintable(printableId);
        printable.setIsdelivered(printableDto.isIsdelivered());
        printable.setPassState(printableDto.getPassState());
        Printable printable2 =  this.printableRepo.save(printable);
        return this.modelMapper.map(printable2, PrintableDto.class);
 
    }

    @Override
    public void deletePrintable(Integer printableId) {
        this.printableRepo.delete(this.fetchPrintable(printableId));
    }

    @Override
    public PrintableDto getPrintableById(Integer printableId) {
        Printable printable = this.fetchPrintable(printableId);
        return this.modelMapper.map(printable, PrintableDto.class); 
        }

    @Override
    public List<PrintableDto> getPrintableByUser(Integer userId) {
        User user = this.userServiceImpl.fetchUser(userId);
        List<Printable> printables = this.printableRepo.findByUser(user);
        List<PrintableDto> printableDtos =  printables.stream().map((print) -> this.modelMapper.map(print, PrintableDto.class)).collect(Collectors.toList());
        return printableDtos;
    }

}
