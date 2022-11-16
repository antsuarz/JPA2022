package uo.ri.cws.application.service.invoice.create.command;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.WorkOrder;
import uo.ri.util.assertion.ArgumentChecks;

public class CreateInvoiceFor implements Command<InvoiceDto>{

	private List<String> workOrderIds;
	private WorkOrderRepository wrkrsRepo = Factory.repository.forWorkOrder();
	private InvoiceRepository invsRepo = Factory.repository.forInvoice();

	public CreateInvoiceFor(List<String> workOrderIds) {
		checkArguments(workOrderIds);
		this.workOrderIds = workOrderIds;
	}

	private void checkArguments(List<String> workOrderIds) {
		ArgumentChecks.isNotNull( workOrderIds );
		ArgumentChecks.isTrue( !workOrderIds.isEmpty() );
		for(String id: workOrderIds) {
			ArgumentChecks.isNotNull(id);
			ArgumentChecks.isNotBlank(id);
		}
	}

	@Override
	public InvoiceDto execute() throws BusinessException {
		
		List<WorkOrder> wol = wrkrsRepo.findByIds(workOrderIds);
		BusinessChecks.isTrue(wol.size() == workOrderIds.size());
		
		for(WorkOrder w : wol) {
			BusinessChecks.isTrue(w.isFinished());
		}
		Long next = invsRepo.getNextInvoiceNumber();
		Invoice invoice = new Invoice(next, wol);
		invsRepo.add(invoice);
		return DtoAssembler.toDto(invoice);
	}

}
