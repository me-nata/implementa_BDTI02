package service;

import DAO.CarroDAO;
import model.Carro;
import spark.Request;
import spark.Response;


public class ProdutoService {

	private CarroDAO carroDAO = new CarroDAO();

	public ProdutoService() {
		carroDAO.conectar();
	}

	public Object add(Request request, Response response) {
		String nome = request.queryParams("nome");
		String marca = request.queryParams("marca");
		int ano = Integer.parseInt(request.queryParams("ano"));
		
		int id = carroDAO.getId();
		
		Carro carro = new Carro(id, nome, marca, ano);

		carroDAO.inserirCarro(carro);

		response.status(201); // 201 Created
		return id;
	}

	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params("id"));
		
		Carro carro = (Carro) carroDAO.getCarro(id);
		
		if (carro != null) {
    	    response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");

            return "<carro>\n" + 
            		"\t<codigo>" + carro.getCodigo() + "</codigo>\n" +
            		"\t<nome>" + carro.getNome() + "</nome>\n" +
            		"\t<marca>" + carro.getMarca() + "</marca>\n" +
            		"\t<ano>" + carro.getAno() + "</ano>\n" +
            		"</carro>\n";
        } else {
            response.status(404); // 404 Not found
            return "Produto " + id + " não encontrado.";
        }

	}

	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        
		Carro carro = (Carro) carroDAO.getCarro(id);

        if (carro != null) {
        	carro.setNome(request.queryParams("nome"));
        	carro.setAno(Integer.parseInt(request.queryParams("ano")));
        	carro.setMarca(request.queryParams("marca"));

        	carroDAO.atualizarCarro(carro);
        	
            return id;
        } else {
            response.status(404); // 404 Not found
            return "Produto não encontrado.";
        }

	}

	public Object remove(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));

        Carro carro = (Carro) carroDAO.getCarro(id);

        if (carro != null) {

            carroDAO.excluirCarro(carro.getCodigo());

            response.status(200); // success
        	return id;
        } else {
            response.status(404); // 404 Not found
            return "Produto não encontrado.";
        }
	}

	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<carros type=\"array\">");
		for (Carro carro : carroDAO.getCarros()) {
			returnValue.append("<carro>\n" + 
            		"\t<codigo>" + carro.getCodigo() + "</codigo>\n" +
            		"\t<nome>" + carro.getNome() + "</nome>\n" +
            		"\t<marca>" + carro.getMarca() + "</marca>\n" +
            		"\t<ano>" + carro.getAno() + "</ano>\n" +
            		"</carro>\n");
		}
		returnValue.append("</carros>");
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
}