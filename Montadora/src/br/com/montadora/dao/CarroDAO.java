package br.com.montadora.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.montadora.model.Carro;

public class CarroDAO {

    private Connection con = null;

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public CarroDAO(Connection con) {
        setCon(con);
    }

    public String inserir(Carro carro) {
        String sql = "insert into carro(montadora, nomecarro, quantidadeportas) values (?,?,?)";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, carro.getMontadora());
            ps.setString(2, carro.getNomeCarro());
            ps.setInt(3, carro.getQuantidadePortas());
            if (ps.executeUpdate() > 0) {
                return "Carro '" + carro.getNomeCarro() + "' inserido com sucesso.";
            } else {
                System.err.println("Erro: executeUpdate retornou 0 ou menos para inserção de " + carro.getNomeCarro());
                return "Erro desconhecido ao tentar inserir o carro.";
            }
        } catch (SQLException e) {
            System.err.println("Erro de SQL ao inserir carro: " + e.getMessage());
            e.printStackTrace();
            return "Erro de SQL ao inserir carro: " + e.getMessage();
        } catch (NullPointerException e) {
            System.err.println("Erro: Dados inválidos fornecidos para inserção (objeto/atributo nulo). " + e.getMessage());
            e.printStackTrace();
            return "Erro: Dados inválidos para inserção.";
        }
    }

    public String deletar(Carro carro) {
        String sql = "delete from carro where montadora = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, carro.getMontadora());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0 ? "Carro(s) da montadora '" + carro.getMontadora() + "' deletado(s) com sucesso (" + affectedRows + " registro(s) afetado(s))." : "Nenhum carro encontrado com a montadora '" + carro.getMontadora() + "' para deletar.";
        } catch (SQLException e) {
            System.err.println("Erro de SQL ao deletar carro por montadora: " + e.getMessage());
            e.printStackTrace();
            return "Erro de SQL ao deletar carro por montadora: " + e.getMessage();
        } catch (NullPointerException e) {
            System.err.println("Erro: Dados inválidos fornecidos para deleção por montadora (objeto/atributo nulo). " + e.getMessage());
            e.printStackTrace();
            return "Erro: Dados inválidos para deleção por montadora.";
        }
    }

    public String deletarWhereNome(Carro carro) {
        String sql = "delete from carro where nomeCarro = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, carro.getNomeCarro());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0 ? "Carro com nome '" + carro.getNomeCarro() + "' deletado com sucesso." : "Nenhum carro encontrado com o nome '" + carro.getNomeCarro() + "' para deletar.";
        } catch (SQLException e) {
            System.err.println("Erro de SQL ao deletar carro por nome: " + e.getMessage());
            e.printStackTrace();
            return "Erro de SQL ao deletar carro por nome: " + e.getMessage();
        } catch (NullPointerException e) {
            System.err.println("Erro: Dados inválidos fornecidos para deleção por nome (objeto/atributo nulo). " + e.getMessage());
            e.printStackTrace();
            return "Erro: Dados inválidos para deleção por nome.";
        }
    }

    public String deletarSemWhere() {
        String sql = "delete from carro";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0 ? "Todos os " + affectedRows + " carro(s) foram deletados com sucesso." : "Nenhum carro encontrado na tabela para deletar (tabela já estava vazia).";
        } catch (SQLException e) {
            System.err.println("Erro de SQL ao tentar deletar todos os carros: " + e.getMessage());
            e.printStackTrace();
            return "Erro de SQL ao tentar deletar todos os carros: " + e.getMessage();
        }
    }

    public String deletarPorId(int id) {
        String sql = "DELETE FROM carro WHERE id_carro = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                return "Carro com ID " + id + " deletado com sucesso.";
            } else {
                return "Nenhum carro encontrado com o ID " + id + " para deletar.";
            }
        } catch (SQLException e) {
            System.err.println("Erro de SQL ao deletar carro por ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            return "Erro de SQL ao deletar carro por ID: " + e.getMessage();
        }
    }

    public String alterar(Carro carro) {
        String sql = "UPDATE carro SET montadora = ?, quantidadeportas = ? WHERE nomecarro = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, carro.getMontadora());
            ps.setInt(2, carro.getQuantidadePortas());
            ps.setString(3, carro.getNomeCarro());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0 ? "Carro '" + carro.getNomeCarro() + "' alterado com sucesso (buscado por nome)." : "Erro ao alterar: Carro com nome '" + carro.getNomeCarro() + "' não encontrado.";
        } catch (SQLException e) {
            System.err.println("Erro de SQL ao tentar alterar carro por nome: " + e.getMessage());
            e.printStackTrace();
            return "Erro de SQL ao tentar alterar carro por nome: " + e.getMessage();
        } catch (NullPointerException e) {
             System.err.println("Erro: Dados inválidos fornecidos para alteração por nome (objeto/atributo nulo). " + e.getMessage());
             e.printStackTrace();
            return "Erro: Dados inválidos para alteração por nome.";
        }
    }

    public String alterarPorId(Carro carro) {
        if (carro == null || carro.getId() <= 0) {
            return "Erro: ID inválido ou objeto Carro nulo fornecido para alteração.";
        }
        if (carro.getMontadora() == null || carro.getMontadora().trim().isEmpty() ||
            carro.getNomeCarro() == null || carro.getNomeCarro().trim().isEmpty()) {
            return "Erro: Montadora e Nome do Carro não podem ser vazios para alteração.";
        }
        String sql = "UPDATE carro SET montadora = ?, quantidadeportas = ?, nomecarro = ? WHERE id_carro = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, carro.getMontadora());
            ps.setInt(2, carro.getQuantidadePortas());
            ps.setString(3, carro.getNomeCarro());
            ps.setInt(4, carro.getId());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                return "Carro com ID " + carro.getId() + " alterado com sucesso.";
            } else {
                return "Erro ao alterar: Carro com ID " + carro.getId() + " não encontrado.";
            }
        } catch (SQLException e) {
            System.err.println("Erro de SQL ao tentar alterar carro por ID " + carro.getId() + ": " + e.getMessage());
            e.printStackTrace();
            return "Erro de SQL ao tentar alterar carro por ID: " + e.getMessage();
        } catch (NullPointerException e) {
             System.err.println("Erro: Dados inválidos fornecidos para alteração por ID (atributo nulo?). " + e.getMessage());
             e.printStackTrace();
            return "Erro: Dados inválidos para alteração por ID.";
        }
    }

    public String alterarMontadora(String montadoraAntiga, String montadoraNova) {
        if (montadoraAntiga == null || montadoraAntiga.trim().isEmpty() ||
            montadoraNova == null || montadoraNova.trim().isEmpty()) {
            return "Erro: Nomes de montadora (antiga e nova) não podem ser vazios.";
        }
        String sql = "UPDATE carro SET montadora = ? WHERE montadora = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, montadoraNova);
            ps.setString(2, montadoraAntiga);
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                return affectedRows + " carro(s) tiveram a montadora alterada de '" + montadoraAntiga + "' para '" + montadoraNova + "'.";
            } else {
                return "Nenhum carro encontrado com a montadora '" + montadoraAntiga + "'. Nenhuma alteração realizada.";
            }
        } catch (SQLException e) {
            System.err.println("Erro de SQL ao tentar alterar montadora de '" + montadoraAntiga + "' para '" + montadoraNova + "': " + e.getMessage());
            e.printStackTrace();
            return "Erro de SQL ao tentar alterar a montadora: " + e.getMessage();
        }
    }

    public List<Carro> selecionarTodos() throws SQLException {
        String sql = "SELECT id_carro, montadora, nomecarro, quantidadeportas FROM carro ORDER BY nomecarro";
        List<Carro> listaCarros = new ArrayList<>();
        try (PreparedStatement ps = getCon().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Carro carro = new Carro();
                carro.setId(rs.getInt("id_carro"));
                carro.setMontadora(rs.getString("montadora"));
                carro.setNomeCarro(rs.getString("nomecarro"));
                carro.setQuantidadePortas(rs.getInt("quantidadeportas"));
                listaCarros.add(carro);
            }
        } catch (SQLException e) {
            System.err.println("Erro de SQL ao selecionar todos os carros: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return listaCarros;
    }

    public Carro selecionarPorNome(String nomeCarro) throws SQLException {
        String sql = "SELECT id_carro, montadora, nomecarro, quantidadeportas FROM carro WHERE nomecarro = ?";
        Carro carroEncontrado = null;
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, nomeCarro);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    carroEncontrado = new Carro();
                    carroEncontrado.setId(rs.getInt("id_carro"));
                    carroEncontrado.setMontadora(rs.getString("montadora"));
                    carroEncontrado.setNomeCarro(rs.getString("nomecarro"));
                    carroEncontrado.setQuantidadePortas(rs.getInt("quantidadeportas"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro de SQL ao selecionar carro por nome '" + nomeCarro + "': " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return carroEncontrado;
    }

    public Carro selecionarPorId(int id) throws SQLException {
        String sql = "SELECT id_carro, montadora, nomecarro, quantidadeportas FROM carro WHERE id_carro = ?";
        Carro carroEncontrado = null;

        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    carroEncontrado = new Carro();
                    carroEncontrado.setId(rs.getInt("id_carro"));
                    carroEncontrado.setMontadora(rs.getString("montadora"));
                    carroEncontrado.setNomeCarro(rs.getString("nomecarro"));
                    carroEncontrado.setQuantidadePortas(rs.getInt("quantidadeportas"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro de SQL ao selecionar carro por ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

        return carroEncontrado;
    }

    public List<Carro> selecionarPorMontadora(String montadora) throws SQLException {
        String sql = "SELECT id_carro, montadora, nomecarro, quantidadeportas FROM carro WHERE montadora = ? ORDER BY nomecarro";
        List<Carro> listaCarros = new ArrayList<>();

        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, montadora);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Carro carro = new Carro();
                    carro.setId(rs.getInt("id_carro"));
                    carro.setMontadora(rs.getString("montadora"));
                    carro.setNomeCarro(rs.getString("nomecarro"));
                    carro.setQuantidadePortas(rs.getInt("quantidadeportas"));
                    listaCarros.add(carro);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro de SQL ao selecionar carros pela montadora '" + montadora + "': " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

        return listaCarros;
    }
} 
