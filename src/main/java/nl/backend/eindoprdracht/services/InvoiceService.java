package nl.backend.eindoprdracht.services;

import nl.backend.eindoprdracht.dtos.invoice.InvoiceInputDto;
import nl.backend.eindoprdracht.dtos.invoice.InvoiceOutputDto;
import nl.backend.eindoprdracht.exceptions.RecordNotFoundException;
import nl.backend.eindoprdracht.models.CustomerAccount;
import nl.backend.eindoprdracht.models.Invoice;
import nl.backend.eindoprdracht.repositories.CustomerAccountRepository;
import nl.backend.eindoprdracht.repositories.InvoiceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class InvoiceService {


    private final InvoiceRepository invoiceRepository;

    private final CustomerAccountRepository customerAccountRepository;

    private final CustomerAccountService customerAccountService;

    public InvoiceService(InvoiceRepository invoiceRepository, CustomerAccountRepository customerAccountRepository, CustomerAccountService customerAccountService) {
        this.invoiceRepository = invoiceRepository;
        this.customerAccountRepository = customerAccountRepository;
        this.customerAccountService = customerAccountService;
    }

    public Invoice dtoTransferToInvoice(InvoiceInputDto invoiceInputDto) {
        Invoice invoice = new Invoice();

        invoice.setTypeOfWork(invoiceInputDto.getTypeOfWork());
        invoice.setPrice(invoiceInputDto.getPrice());
        invoice.setTypeOfProduct(invoiceInputDto.getTypeOfProduct());
        invoice.setBusinessTaxNumber(invoiceInputDto.getBusinessTaxNumber());
        invoice.setAmount(invoiceInputDto.getAmount());
        invoice.setTaxAmount(invoiceInputDto.getTaxAmount());
        invoice.setDate(invoiceInputDto.getDate());
        invoice.setBusinessAddress(invoiceInputDto.getBusinessAddress());
        invoice.setCustomerAddress(invoiceInputDto.getCustomerAddress());
        invoice.setTermOfPayment(invoiceInputDto.getTermOfPayment());
        return invoice;
    }

    public InvoiceOutputDto invoiceTransferToDto(Invoice invoice) {
        InvoiceOutputDto dto = new InvoiceOutputDto();

        dto.id = invoice.getId();
        dto.typeOfWork = invoice.getTypeOfWork();
        dto.price = invoice.getPrice();
        dto.typeOfProduct = invoice.getTypeOfProduct();
        dto.businessTaxNumber = invoice.getBusinessTaxNumber();
        dto.amount = invoice.getAmount();
        dto.taxAmount = invoice.getTaxAmount();
        dto.date = invoice.getDate();
        dto.businessAddress = invoice.getBusinessAddress();
        dto.customerAddress = invoice.getCustomerAddress();
        dto.termOfPayment = invoice.getTermOfPayment();

        if (invoice.getCustomerAccount() != null) {
            dto.setCustomerAccountOutputDto(customerAccountService.customerAccountTransferCustomerAccountOutputDto(invoice.getCustomerAccount()));
        }

        return dto;
    }

    public InvoiceOutputDto createInvoice(InvoiceInputDto inputDto) {
        Invoice invoice = dtoTransferToInvoice(inputDto);
        invoiceRepository.save(invoice);
        return invoiceTransferToDto(invoice);
    }

    public InvoiceOutputDto getInvoice(long id) {
        Optional<Invoice> invoiceId = invoiceRepository.findById(id);

        if (invoiceId.isPresent()) {
            Invoice invoice = invoiceId.get();
            InvoiceOutputDto dto = invoiceTransferToDto(invoice);
            return dto;
        } else {
            throw new RecordNotFoundException("No invoice id found" + id);
        }
    }

    public List<InvoiceOutputDto> getAllInvoice() {
        List<Invoice> invoiceList = invoiceRepository.findAll();
        List<InvoiceOutputDto> invoiceOutputDtoList = new ArrayList<>();
        for (Invoice invoice : invoiceList) {
            invoiceOutputDtoList.add(invoiceTransferToDto(invoice));

        }
        return invoiceOutputDtoList;
    }

    public List<InvoiceOutputDto> getAllInvoiceByCustomerId(Long caId){
        List<Invoice> invoiceList = invoiceRepository.findByCustomerAccountId(caId);
        List<InvoiceOutputDto> invoiceOutputDtoList = new ArrayList<>();
        for(Invoice invoice : invoiceList) {
            invoiceOutputDtoList.add(invoiceTransferToDto(invoice));
        }
        return invoiceOutputDtoList;
    }

    public InvoiceOutputDto updateInvoice(long id, InvoiceInputDto invoice) {
        Optional<Invoice> getInvoice = invoiceRepository.findById(id);
        if (getInvoice.isEmpty()) {
            throw new RecordNotFoundException("No invoice found by id " + id);
        } else {
            Invoice changeInvoice1 = getInvoice.get();
            if (invoice.getTypeOfWork() != null) {
                changeInvoice1.setTypeOfWork(invoice.getTypeOfWork());
            }
            if (invoice.getPrice() != null) {
                changeInvoice1.setPrice(invoice.getPrice());
            }
            if (invoice.getTypeOfProduct() != null) {
                changeInvoice1.setTypeOfProduct(invoice.getTypeOfProduct());
            }
            if (invoice.getBusinessTaxNumber() != null) {
                changeInvoice1.setBusinessTaxNumber(invoice.getBusinessTaxNumber());
            }
            if (invoice.getAmount() != null) {
                changeInvoice1.setAmount(invoice.getAmount());
            }
            if (invoice.getTaxAmount() != null) {
                changeInvoice1.setTaxAmount(invoice.getTaxAmount());
            }
            if (invoice.getDate() != null) {
                changeInvoice1.setDate(invoice.getDate());
            }
            if (invoice.getBusinessAddress() != null) {
                changeInvoice1.setBusinessAddress(invoice.getBusinessAddress());
            }
            if (invoice.getCustomerAddress() != null) {
                changeInvoice1.setCustomerAddress(invoice.getCustomerAddress());
            }
            if (invoice.getTermOfPayment() != null) {
                changeInvoice1.setTermOfPayment(invoice.getTermOfPayment());
            }
            Invoice returnInvoice = invoiceRepository.save(changeInvoice1);
            return invoiceTransferToDto(returnInvoice);
        }
    }

    public void assignCustomerToInvoice(long invoiceId, long customerId) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(invoiceId);
        Optional<CustomerAccount> optionalCustomerAccount = customerAccountRepository.findById(customerId);
        if (optionalInvoice.isPresent() && optionalCustomerAccount.isPresent()) {
            Invoice invoice = optionalInvoice.get();
            CustomerAccount customerAccount = optionalCustomerAccount.get();
            invoice.setCustomerAccount(customerAccount);
            invoiceRepository.save(invoice);
        } else {
            throw new RecordNotFoundException("No combination invoice customer is found");
        }

    }


    public void deleteInvoice(long id) {
        invoiceRepository.deleteById(id);
    }



}
