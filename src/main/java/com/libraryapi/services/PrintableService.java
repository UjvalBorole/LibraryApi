package com.libraryapi.services;

import com.libraryapi.payloads.PrintableDto;
import java.util.*;

import com.libraryapi.entities.Printable;

public interface PrintableService {
    PrintableDto createPrintable(Integer userId,Integer bookId,PrintableDto printableDto);
    PrintableDto updatePrintable(Integer printableId ,PrintableDto printableDto);
    PrintableDto updatePrintableByAdmin(Integer printableId ,PrintableDto printableDto);
    void deletePrintable(Integer printableId);
    PrintableDto getPrintableById(Integer printableId);
    List<PrintableDto> getPrintableByUser(Integer userId);

}
