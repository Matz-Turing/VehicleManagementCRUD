package br.com.montadora.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import br.com.montadora.model.Moto;

public class MotoDAO {

    private Connection con = null;

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public MotoDAO(Connection con) {
        setCon(con);
    }

    public String inserir(Moto moto) {
        String sql = "insert into moto(montadora, nomemoto, quantidadeadesivos) values (?,?,?)";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, moto.getMontadora());
            ps.setString(2, moto.getNomeCarro());
            ps.setInt(3, moto.getQuantidadeAdesivos());
            if (ps.executeUpdate() > 0) {
                return "Moto '" + moto.getNomeCarro() + "' inserida com sucesso.";
            } else {
                return "Erro desconhecido ao tentar inserir a moto.";
            }
        } catch (SQLException | NullPointerException e) {
            System.err.println("Erro ao inserir moto: " + e.getMessage());
            e.printStackTrace();
            return "Erro ao inserir moto: " + e.getMessage();
        }
    }

    public String deletar(Moto moto) {
        String sql = "delete from moto where montadora = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, moto.getMontadora());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                return "Moto(s) da montadora '" + moto.getMontadora() + "' deletada(s) com sucesso (" + affectedRows + " registro(s) afetado(s)).";
            } else {
                return "Nenhuma moto encontrada com a montadora '" + moto.getMontadora() + "' para deletar.";
            }
        } catch (SQLException | NullPointerException e) {
            System.err.println("Erro ao deletar moto por montadora: " + e.getMessage());
            e.printStackTrace();
            return "Erro ao deletar moto por montadora: " + e.getMessage();
        }
    }

    public String deletarWhereNome(Moto moto) {
        String sql = "delete from moto where nomemoto = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, moto.getNomeCarro());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                return "Moto com nome '" + moto.getNomeCarro() + "' deletada com sucesso.";
            } else {
                return "Nenhuma moto encontrada com o nome '" + moto.getNomeCarro() + "' para deletar.";
            }
        } catch (SQLException | NullPointerException e) {
            System.err.println("Erro ao deletar moto por nome: " + e.getMessage());
            e.printStackTrace();
            return "Erro ao deletar moto por nome: " + e.getMessage();
        }
    }

    public String deletarSemWhere() {
        String sql = "delete from moto";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                return "Todas as " + affectedRows + " moto(s) foram deletadas com sucesso.";
            } else {
                return "Nenhuma moto encontrada na tabela para deletar (tabela já estava vazia).";
            }
        } catch (SQLException e) {
            System.err.println("Erro ao deletar todas as motos: " + e.getMessage());
            e.printStackTrace();
            return "Erro ao deletar todas as motos: " + e.getMessage();
        }
    }

    public String deletarPorId(int id) {
        String sql = "DELETE FROM moto WHERE id_moto = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                return "Moto com ID " + id + " deletada com sucesso.";
            } else {
                return "Nenhuma moto encontrada com o ID " + id + " para deletar.";
            }
        } catch (SQLException e) {
            System.err.println("Erro ao deletar moto por ID: " + e.getMessage());
            e.printStackTrace();
            return "Erro ao deletar moto por ID: " + e.getMessage();
        }
    }

    public String alterar(Moto moto) {
        String sql = "UPDATE moto SET montadora = ?, quantidadeadesivos = ? WHERE nomemoto = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, moto.getMontadora());
            ps.setInt(2, moto.getQuantidadeAdesivos());
            ps.setString(3, moto.getNomeCarro());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                return "Moto '" + moto.getNomeCarro() + "' alterada com sucesso (buscada por nome).";
            } else {
                return "Erro ao alterar: Moto com nome '" + moto.getNomeCarro() + "' não encontrada.";
            }
        } catch (SQLException | NullPointerException e) {
            System.err.println("Erro ao alterar moto por nome: " + e.getMessage());
            e.printStackTrace();
            return "Erro ao alterar moto por nome: " + e.getMessage();
        }
    }

    public String alterarPorId(Moto moto) {
        if (moto == null || moto.getId() <= 0) {
            return "Erro: ID inválido ou objeto Moto nulo fornecido para alteração.";
        }
        if (moto.getMontadora() == null || moto.getMontadora().trim().isEmpty() ||
            moto.getNomeCarro() == null || moto.getNomeCarro().trim().isEmpty()) {
            return "Erro: Montadora e Nome/Modelo da Moto não podem ser vazios para alteração.";
        }
        String sql = "UPDATE moto SET montadora = ?, quantidadeadesivos = ?, nomemoto = ? WHERE id_moto = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, moto.getMontadora());
            ps.setInt(2, moto.getQuantidadeAdesivos());
            ps.setString(3, moto.getNomeCarro());
            ps.setInt(4, moto.getId());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                return "Moto com ID " + moto.getId() + " alterada com sucesso.";
            } else {
                return "Erro ao alterar: Moto com ID " + moto.getId() + " não encontrada.";
            }
        } catch (SQLException | NullPointerException e) {
            System.err.println("Erro ao tentar alterar moto por ID: " + e.getMessage());
            e.printStackTrace();
            return "Erro ao tentar alterar moto por ID: " + e.getMessage();
        }
    }

    public String alterarMontadora(String montadoraAntiga, String montadoraNova) {
        if (montadoraAntiga == null || montadoraAntiga.trim().isEmpty() ||
            montadoraNova == null || montadoraNova.trim().isEmpty()) {
            return "Erro: Nomes de montadora (antiga e nova) não podem ser vazios.";
        }
        if (montadoraAntiga.trim().equalsIgnoreCase(montadoraNova.trim())) {
            return "Aviso: A montadora antiga e a nova são as mesmas. Nenhuma alteração necessária.";
        }
        String sql = "UPDATE moto SET montadora = ? WHERE montadora = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, montadoraNova.trim());
            ps.setString(2, montadoraAntiga.trim());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                return affectedRows + " moto(s) tiveram a montadora alterada de '" + montadoraAntiga.trim() + "' para '" + montadoraNova.trim() + "'.";
            } else {
                return "Nenhuma moto encontrada com a montadora '" + montadoraAntiga.trim() + "'. Nenhuma alteração realizada.";
            }
        } catch (SQLException e) {
            System.err.println("Erro ao tentar alterar a montadora: " + e.getMessage());
            e.printStackTrace();
            return "Erro ao tentar alterar a montadora: " + e.getMessage();
        }
    }

    public Moto selecionarPorId(int id) throws SQLException {
        String sql = "SELECT id_moto, montadora, nomemoto, quantidadeadesivos FROM moto WHERE id_moto = ?";
        Moto motoEncontrada = null;
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    motoEncontrada = new Moto();
                    motoEncontrada.setId(rs.getInt("id_moto"));
                    motoEncontrada.setMontadora(rs.getString("montadora"));
                    motoEncontrada.setNomeCarro(rs.getString("nomemoto"));
                    motoEncontrada.setQuantidadeAdesivos(rs.getInt("quantidadeadesivos"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro de SQL ao selecionar moto por ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return motoEncontrada;
    }

    public List<Moto> selecionarPorMontadora(String montadora) throws SQLException {
        String sql = "SELECT id_moto, montadora, nomemoto, quantidadeadesivos FROM moto WHERE montadora = ? ORDER BY nomemoto";
        List<Moto> listaMotos = new ArrayList<>();
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, montadora);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Moto moto = new Moto();
                    moto.setId(rs.getInt("id_moto"));
                    moto.setMontadora(rs.getString("montadora"));
                    moto.setNomeCarro(rs.getString("nomemoto"));
                    moto.setQuantidadeAdesivos(rs.getInt("quantidadeadesivos"));
                    listaMotos.add(moto);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro de SQL ao selecionar motos pela montadora '" + montadora + "': " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return listaMotos;
    }
}
