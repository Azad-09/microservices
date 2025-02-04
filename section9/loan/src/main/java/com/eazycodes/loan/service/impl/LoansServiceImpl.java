package com.eazycodes.loan.service.impl;

import com.eazycodes.loan.constants.LoansConstants;
import com.eazycodes.loan.dto.LoansDto;
import com.eazycodes.loan.entity.Loans;
import com.eazycodes.loan.exception.LoansAlreadyExistException;
import com.eazycodes.loan.exception.ResourceNotFoundException;
import com.eazycodes.loan.mapper.LoansMapper;
import com.eazycodes.loan.repository.LoansRepository;
import com.eazycodes.loan.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoansService {

    private LoansRepository loansRepository;

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> loans = loansRepository.findByMobileNumber(mobileNumber);

        if (loans.isPresent()){
            throw new LoansAlreadyExistException("Loans already registered with the given number: " + mobileNumber);
        }
        
        loansRepository.save(createNewLoan(mobileNumber));
    }

    private Loans createNewLoan(String mobileNumber) {
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> {
                    throw new ResourceNotFoundException("Loans", "mobileNumber", mobileNumber);
                }
        );
        return LoansMapper.mapToLoansDto(loans, new LoansDto());
    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {
        Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                () -> {
                    throw new ResourceNotFoundException("Loans", "LoanNumber", loansDto.getLoanNumber());
                }
        );
        loans = LoansMapper.mapToLoans(loans, loansDto);
        System.out.println("loans after update: " + loans);
        loansRepository.save(loans);
        return true;
    }

    @Override
    public boolean deleteLoans(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> {
                    throw new ResourceNotFoundException("Loans", "mobileNumber", mobileNumber);
                }
        );

        loansRepository.deleteById(loans.getLoanId());
        return true;
    }


}
