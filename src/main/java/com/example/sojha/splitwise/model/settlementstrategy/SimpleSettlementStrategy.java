package com.example.sojha.splitwise.model.settlementstrategy;

import com.example.sojha.splitwise.constant.ExpenseDistributionStatus;
import com.example.sojha.splitwise.model.entity.ExpenseDistribution;
import com.example.sojha.splitwise.model.entity.ExpenseSettlement;
import com.example.sojha.splitwise.model.entity.SplitwiseUser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Qualifier("simple")
public class SimpleSettlementStrategy implements SettlementStrategy {
    @Override
    public List<ExpenseSettlement> settle(List<ExpenseDistribution> rawDistributions) throws Exception {
        Map<Long, ExpenseDistribution> userToNetDistributionMap = getUserToNetDistributionMap(rawDistributions);

        List<ExpenseDistribution> netPaid = new ArrayList<>();
        List<ExpenseDistribution> netOwes = new ArrayList<>();
        int totalPaid = 0, totalOwed = 0;

        for(ExpenseDistribution netDistribution: userToNetDistributionMap.values()) {
            if (netDistribution.getStatus() == ExpenseDistributionStatus.PAID) {
                netPaid.add(netDistribution);
                totalPaid += netDistribution.getAmount();
            }
            else {
                netOwes.add(netDistribution);
                totalOwed += netDistribution.getAmount();
            }
        }

        // Assert that the net distributions have no mismatch in totalPaid and totalOwed amount.
        // TODO: should be covered by tests and not verified here.
        if (totalPaid != totalOwed) {
            throw new Exception("Mismatch in generating net distribution");
        }

        return getExpenseSettlements(netPaid, netOwes);
    }

    private List<ExpenseSettlement> getExpenseSettlements(
            List<ExpenseDistribution> netPaid,
            List<ExpenseDistribution> netOwes
    ) throws Exception {
        List<ExpenseSettlement> settlements = new ArrayList<>();

        int paidIndex = 0, owesIndex = 0;
        Long paymentNeeded = netPaid.get(0).getAmount();
        Long paymentAvailable = netOwes.get(0).getAmount();

        while(paymentNeeded != 0) {
            Long paymentMade = Math.min(paymentNeeded, paymentAvailable);
            SplitwiseUser payer = netOwes.get(owesIndex).getUser();
            SplitwiseUser payee = netPaid.get(paidIndex).getUser();

            ExpenseSettlement settlement = new ExpenseSettlement(payer, payee, paymentMade);
            settlements.add(settlement);

            // Update values
            paymentNeeded = paymentNeeded - paymentMade;
            if(paymentNeeded == 0) {
                paidIndex++;

                if (paidIndex < netPaid.size())
                    paymentNeeded = netPaid.get(paidIndex).getAmount();
            }

            paymentAvailable = paymentAvailable - paymentMade;
            if(paymentAvailable == 0) {
                owesIndex++;

                if (owesIndex < netOwes.size())
                    paymentAvailable = netOwes.get(owesIndex).getAmount();
            }
        }

        // Assert that we used all the distributions in netPaid and netOwes
        // TODO: should be covered by tests and not verified here.
        if (paidIndex != netPaid.size()
                && owesIndex != netOwes.size()
                && paymentNeeded != 0
                && paymentAvailable != 0
        ) {
            throw new Exception("All distributions not used");
        }

        return settlements;
    }

    private  Map<Long, ExpenseDistribution> getUserToNetDistributionMap(List<ExpenseDistribution> rawDistributions) {
        Map<Long, ExpenseDistribution> userToNetDistributionMap = new HashMap<>();

        for(ExpenseDistribution distribution: rawDistributions) {
            Long userId = distribution.getUser().getId();

            if(!userToNetDistributionMap.containsKey(userId)) {
                userToNetDistributionMap.put(userId, distribution);
                continue;
            }

            // NOTE: Reaching here means that the user both paid and owed in the expense.

            // Remove it from the map first as we will insert a new net Distribution object.
            // Do not modify the existing one as it they are referenced by Expense entity
            // and will be persisted as such.
            ExpenseDistribution currentDistribution = userToNetDistributionMap.remove(userId);

            // Swap distribution such that [currentDistribution, distribution] are
            // in this [PAID distribution, OWES distribution] order
            if (currentDistribution.getStatus() == ExpenseDistributionStatus.OWES) {
                ExpenseDistribution tempDistribution = currentDistribution;
                currentDistribution = distribution;
                distribution = tempDistribution;
            }

            Long netAmount = currentDistribution.getAmount() - distribution.getAmount();

            // The user owes nothing in this case
            if (netAmount == 0) {
                continue;
            }

            ExpenseDistributionStatus netStatus = netAmount > 0
                    ? ExpenseDistributionStatus.PAID
                    : ExpenseDistributionStatus.OWES;

            ExpenseDistribution netDistribution = new ExpenseDistribution(
                    distribution.getUser(),
                    netStatus,
                    Math.abs(netAmount)
            );

            userToNetDistributionMap.put(userId, netDistribution);
        }

        return userToNetDistributionMap;
    }
}
