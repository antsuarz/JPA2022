package uo.ri.cws.application.service.client.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Address;
import uo.ri.cws.domain.Cash;
import uo.ri.cws.domain.Client;
import uo.ri.util.assertion.ArgumentChecks;

public class AddClient implements Command<ClientDto>{

	private ClientDto client; 

	public AddClient(ClientDto client) {
		clientCheck(client); 
		this.client = client; 
	}

	private void clientCheck(ClientDto client) {
		ArgumentChecks.isNotNull(client);
		ArgumentChecks.isNotEmpty(client.dni);
	}
	
	@Override
	public ClientDto execute() throws BusinessException {
		ClientRepository cr = Factory.repository.forClient(); 
  

		Client cliente = createClient();
		cr.add(cliente);
		addPaymentmean(cliente);

		client.id = cliente.getId();
		return client;
	}

	private Client createClient() {
		return new Client(client.dni, client.name, client.surname, client.email, client.phone,
				new Address(client.addressStreet, client.addressCity, client.addressZipcode));
	}
	
	private void addPaymentmean(Client client) {
		PaymentMeanRepository pr = Factory.repository.forPaymentMean();
		Cash c = new Cash(client);
		pr.add(c);
	}
}
