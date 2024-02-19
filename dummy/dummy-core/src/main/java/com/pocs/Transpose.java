package com.pocs;

import com.pocs.Transpose.PaymentInstrument.PaymentOption;
import lombok.val;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.pocs.Transpose.PaymentInstrument.PaymentOption.BANK_TRANSFER;
import static com.pocs.Transpose.PaymentInstrument.PaymentOption.CRYPTO;
import static com.pocs.Transpose.PaymentInstrument.Type.CARD;
import static com.pocs.Transpose.PaymentInstrument.Type.UK_BANK_ACCOUNT;
import static com.pocs.Transpose.PaymentInstrument.Type.US_BANK_ACCOUNT;


public class Transpose {

    public static void main(String[] args) {

        List<PaymentInstrument> instruments = List.of(
                new PaymentInstrument(UK_BANK_ACCOUNT, List.of(BANK_TRANSFER, CRYPTO)),
                new PaymentInstrument(CARD, List.of(PaymentOption.CARD)),
                new PaymentInstrument(US_BANK_ACCOUNT, List.of(BANK_TRANSFER, CRYPTO)));

        Map<PaymentOption, List<PaymentInstrument>> result = new HashMap<>();
        // VERSION 1
        instruments
                .stream()
                .collect(Collectors.toMap(instrument -> instrument, PaymentInstrument::options))
                .forEach((instrument, options) ->
                        options.forEach(option -> result.computeIfAbsent(option, list -> new ArrayList<>()).add(instrument)));

        result.clear();
        // VERSION 2
        val collect = instruments
                .stream()
                .collect(Collectors.toMap(instrument -> instrument, PaymentInstrument::options));

        for(val instrument : collect.entrySet()) {
            for(val option : instrument.getValue()) {
                result.computeIfAbsent(option, list -> new ArrayList<>()).add(instrument.getKey());
            }
        }

        System.out.println(result);

    }

    public record PaymentInstrument(Type type, List<PaymentOption> options) {

        public enum Type {
            SEPA_BANK_ACCOUNT,
            UK_BANK_ACCOUNT,
            US_BANK_ACCOUNT,
            CARD;
        }

        public enum PaymentOption {
            BANK_TRANSFER,
            CARD,
            CRYPTO;
        }
    }
}