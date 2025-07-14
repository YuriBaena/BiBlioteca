import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import lp2g35.biblioteca.*;
import lp2g35.exc.*;

import java.time.LocalDate;

public class LocDev extends JFrame {

    Color cinzaClaro = new Color(220, 220, 220);
    Color azulPastel = new Color(173, 216, 230);
    Color brancoGelo = new Color(245, 245, 245);
    Color begeClaro = new Color(255, 239, 213);
    Color verdePastel = new Color(144, 238, 144);
    Color vermelhoPastel = new Color(255, 182, 193);

    private Biblioteca biblioteca;
    //se função igual a 1 então tela de alocação
    //se função igual a 2 então tela de devolução
    private int funcao;

    private boolean atualizando = false;

    private JComboBox<String> campoBuscaLivro;
    private JRadioButton rbTitulo;

    public LocDev(String nome, Biblioteca b, int f) {
        biblioteca = b;
        funcao = f;
        this.setTitle(nome);
        this.setSize(500, 600);
        this.setLayout(null);
        this.setLocation(500, 0);
        this.getContentPane().setBackground(brancoGelo);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLayeredPane paines = new JLayeredPane();
        paines.setBounds(0, 0, 500, 600);

        // Título
        JPanel titulo = new JPanel();
        titulo.setBounds(50, 20, 400, 80);
        titulo.setBackground(azulPastel);
        titulo.setLayout(null);

        JLabel lblTitulo = new JLabel("• " + nome + " •", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Bradley Hand", Font.PLAIN, 38));
        lblTitulo.setForeground(Color.BLACK);
        lblTitulo.setBounds(0, 0, 400, 80);
        titulo.add(lblTitulo);

        // Painel Usuários
        JPanel painelUsuarios = new JPanel();
        painelUsuarios.setBounds(20, 120, 460, 150);
        painelUsuarios.setBackground(begeClaro);
        painelUsuarios.setLayout(null);

        JLabel lblUsuarios = new JLabel("Usuários:");
        lblUsuarios.setFont(new Font("Bradley Hand", Font.PLAIN, 24));
        lblUsuarios.setBounds(20, 10, 150, 30);
        painelUsuarios.add(lblUsuarios);

        JRadioButton rbNome = new JRadioButton("Nome");
        JRadioButton rbCPF = new JRadioButton("CPF");
        rbNome.setBounds(30, 50, 100, 25);
        rbCPF.setBounds(150, 50, 100, 25);
        rbNome.setBackground(begeClaro);
        rbCPF.setBackground(begeClaro);
        rbNome.setSelected(true);

        ButtonGroup grupoUsuarios = new ButtonGroup();
        grupoUsuarios.add(rbNome);
        grupoUsuarios.add(rbCPF);

        painelUsuarios.add(rbNome);
        painelUsuarios.add(rbCPF);

        JComboBox<String> campoBuscaUsuario = new JComboBox<>();
        campoBuscaUsuario.setEditable(true);
        campoBuscaUsuario.setBounds(30, 90, 400, 30);
        campoBuscaUsuario.addItemListener(e -> {
            if (funcao == 2 && e.getStateChange() == ItemEvent.SELECTED && !atualizando) {
                atualizando = true;
                try {
                    String usuarioInput = e.getItem().toString().trim();
                    Usuario user = null;

                    if (rbNome.isSelected()) {
                        for (Usuario u : biblioteca.getUsuarios().values()) {
                            if (u.getNome().equalsIgnoreCase(usuarioInput)) {
                                user = u;
                                break;
                            }
                        }
                    } else {
                        try {
                            long cpfBusca = Long.parseLong(usuarioInput.replaceAll("\\D", ""));
                            user = biblioteca.getUsuario(cpfBusca);
                        } catch (Exception ex) {
                            return;
                        }
                    }

                    if (user != null) {
                        campoBuscaLivro.removeAllItems();
                        for (Livro l : biblioteca.getLivrosEmprestadosPorUsuario(user)) {
                            if (rbTitulo.isSelected()) {
                                campoBuscaLivro.addItem(l.getTitulo());
                            } else {
                                campoBuscaLivro.addItem(String.valueOf(l.getCodigo()));
                            }
                        }
                    }
                } finally {
                    atualizando = false;
                }
            }
        });


        painelUsuarios.add(campoBuscaUsuario);

        // Painel Livros
        JPanel painelLivros = new JPanel();
        painelLivros.setBounds(20, 290, 460, 150);
        painelLivros.setBackground(begeClaro);
        painelLivros.setLayout(null);

        JLabel lblLivros = new JLabel("Livros:");
        lblLivros.setFont(new Font("Bradley Hand", Font.PLAIN, 24));
        lblLivros.setBounds(20, 10, 150, 30);
        painelLivros.add(lblLivros);

        rbTitulo = new JRadioButton("Título");
        JRadioButton rbCodigo = new JRadioButton("Código");
        rbTitulo.setBounds(30, 50, 100, 25);
        rbCodigo.setBounds(150, 50, 100, 25);
        rbTitulo.setBackground(begeClaro);
        rbCodigo.setBackground(begeClaro);
        rbTitulo.setSelected(true);

        ButtonGroup grupoLivros = new ButtonGroup();
        grupoLivros.add(rbTitulo);
        grupoLivros.add(rbCodigo);

        painelLivros.add(rbTitulo);
        painelLivros.add(rbCodigo);

        campoBuscaLivro = new JComboBox<>();
        campoBuscaLivro.setEditable(true);
        campoBuscaLivro.setBounds(30, 90, 400, 30);
        campoBuscaLivro.addItemListener(e -> {
            if (funcao == 2 && e.getStateChange() == ItemEvent.SELECTED && !atualizando) {
                atualizando = true;
                try {
                    String livroInput = e.getItem().toString().trim();
                    Livro livro = null;

                    if (rbTitulo.isSelected()) {
                        for (Livro l : biblioteca.getLivros().values()) {
                            if (l.getTitulo().equalsIgnoreCase(livroInput)) {
                                livro = l;
                                break;
                            }
                        }
                    } else {
                        try {
                            int codBusca = Integer.parseInt(livroInput);
                            livro = biblioteca.getLivro(codBusca);
                        } catch (Exception ex) {
                            return;
                        }
                    }

                    if (livro != null) {
                        campoBuscaUsuario.removeAllItems();
                        for (Usuario u : biblioteca.getUsuariosComLivroEmprestado(livro)) {
                            if (rbNome.isSelected()) {
                                campoBuscaUsuario.addItem(u.getNome());
                            } else {
                                campoBuscaUsuario.addItem(String.valueOf(u.getCPF()));
                            }
                        }
                    }
                } finally {
                    atualizando = false;
                }
            }
        });


        painelLivros.add(campoBuscaLivro);

        // Função para atualizar as sugestões
        Runnable atualizarSugestoesUsuarios = () -> {
            campoBuscaUsuario.removeAllItems();

            if (funcao == 2) {  // DEVOLUÇÃO
                // Só mostrar usuários que têm livros emprestados
                for (Usuario u : biblioteca.getUsuarios().values()) {
                    if (!biblioteca.getLivrosEmprestadosPorUsuario(u).isEmpty()) {
                        if (rbNome.isSelected()) {
                            campoBuscaUsuario.addItem(u.getNome());
                        } else {
                            campoBuscaUsuario.addItem(String.valueOf(u.getCPF()));
                        }
                    }
                }
            } else {
                // Normal (alocação)
                for (Usuario u : biblioteca.getUsuarios().values()) {
                    if (rbNome.isSelected()) {
                        campoBuscaUsuario.addItem(u.getNome());
                    } else {
                        campoBuscaUsuario.addItem(String.valueOf(u.getCPF()));
                    }
                }
            }
        };

        Runnable atualizarSugestoesLivros = () -> {
            campoBuscaLivro.removeAllItems();

            if (funcao == 2) {  // DEVOLUÇÃO
                // Só mostrar livros que têm pelo menos uma cópia emprestada
                for (Livro l : biblioteca.getLivros().values()) {
                    if (l.getEmprestados() > 0) {
                        if (rbTitulo.isSelected()) {
                            campoBuscaLivro.addItem(l.getTitulo());
                        } else {
                            campoBuscaLivro.addItem(String.valueOf(l.getCodigo()));
                        }
                    }
                }
            } else {
                // Normal (alocação)
                for (Livro l : biblioteca.getLivros().values()) {
                    if (rbTitulo.isSelected()) {
                        campoBuscaLivro.addItem(l.getTitulo());
                    } else {
                        campoBuscaLivro.addItem(String.valueOf(l.getCodigo()));
                    }
                }
            }
        };


        // Atualizar ao abrir
        atualizarSugestoesUsuarios.run();
        atualizarSugestoesLivros.run();

        // Atualizar quando o radio mudar
        rbNome.addActionListener(e -> atualizarSugestoesUsuarios.run());
        rbCPF.addActionListener(e -> atualizarSugestoesUsuarios.run());
        rbTitulo.addActionListener(e -> atualizarSugestoesLivros.run());
        rbCodigo.addActionListener(e -> atualizarSugestoesLivros.run());

        // Botão Confirmar
        JButton botaoConfirmar = new JButton("Confirmar");
        botaoConfirmar.setBounds(60, 470, 160, 40);
        botaoConfirmar.setBackground(verdePastel);
        botaoConfirmar.setFont(new Font("Bradley Hand", Font.PLAIN, 22));
        botaoConfirmar.setFocusPainted(false);
        botaoConfirmar.setOpaque(true);
        botaoConfirmar.setBorderPainted(false);
        botaoConfirmar.addActionListener(e -> {
            try {
                String usuarioInput = campoBuscaUsuario.getEditor().getItem().toString().trim();
                String livroInput = campoBuscaLivro.getEditor().getItem().toString().trim();

                Usuario user = null;
                if (rbNome.isSelected()) {
                    for (Usuario u : biblioteca.getUsuarios().values()) {
                        if (u.getNome().equalsIgnoreCase(usuarioInput)) {
                            user = u;
                            break;
                        }
                    }
                } else {
                    try {
                        long cpfBusca = Long.parseLong(usuarioInput.replaceAll("\\D", ""));
                        user = biblioteca.getUsuario(cpfBusca);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "CPF inválido ou não encontrado.");
                        return;
                    }
                }

                if (user == null) {
                    JOptionPane.showMessageDialog(this, "Usuário não encontrado.");
                    return;
                }

                Livro livro = null;
                if (rbTitulo.isSelected()) {
                    for (Livro l : biblioteca.getLivros().values()) {
                        if (l.getTitulo().equalsIgnoreCase(livroInput)) {
                            livro = l;
                            break;
                        }
                    }
                } else {
                    try {
                        int codBusca = Integer.parseInt(livroInput);
                        livro = biblioteca.getLivro(codBusca);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Código inválido ou livro não encontrado.");
                        return;
                    }
                }

                if (livro == null) {
                    JOptionPane.showMessageDialog(this, "Livro não encontrado.");
                    return;
                }

                usa(user,livro);
                this.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        // Botão Cancelar
        JButton botaoCancelar = new JButton("Cancelar");
        botaoCancelar.setBounds(260, 470, 160, 40);
        botaoCancelar.setBackground(vermelhoPastel);
        botaoCancelar.setFont(new Font("Bradley Hand", Font.PLAIN, 22));
        botaoCancelar.setFocusPainted(false);
        botaoCancelar.setOpaque(true);
        botaoCancelar.setBorderPainted(false);
        botaoCancelar.addActionListener(e -> this.dispose());

        // Créditos
        JPanel creditos = new JPanel();
        creditos.setBounds(120, 530, 260, 27);
        creditos.setBackground(azulPastel);
        JLabel cr = new JLabel("Yuri Baena 2025.1", SwingConstants.CENTER);
        cr.setForeground(Color.BLACK);
        creditos.add(cr);

        // Adiciona tudo
        paines.add(titulo, 0);
        paines.add(painelUsuarios, 1);
        paines.add(painelLivros, 2);
        paines.add(botaoConfirmar, 3);
        paines.add(botaoCancelar, 4);
        paines.add(creditos, 5);

        this.add(paines);
        this.setVisible(true);
    }

    private void usa(Usuario user, Livro livro) {
        try {
            if (funcao == 1) {  // EMPRESTAR
                LocalDate data = biblioteca.emprestaLivro(user, livro);
                JOptionPane.showMessageDialog(this, "Livro emprestado com sucesso em " + data + " para " + user.getNome());
            } else if (funcao == 2) {  // DEVOLVER
                LocalDate data = biblioteca.devolveLivro(user, livro);
                JOptionPane.showMessageDialog(this, "Livro devolvido com sucesso em " + data + " por " + user.getNome());
            } else {
                JOptionPane.showMessageDialog(this, "Função inválida. Use 1 para emprestar ou 2 para devolver.");
            }
        } catch (LimiteMaxEmprestadoEx e) {
            JOptionPane.showMessageDialog(this, "O usuário já atingiu o limite máximo de livros emprestados.");
        } catch (CopiaNaoDisponivelEx e) {
            JOptionPane.showMessageDialog(this, "Não há cópias disponíveis deste livro.");
        } catch (NenhumaCopiaEmprestadaEx e) {
            JOptionPane.showMessageDialog(this, "Não há nenhuma cópia emprestada deste livro para devolver.");
        } catch (NaoAlocouLivroEx e) {
            JOptionPane.showMessageDialog(this, "Este usuário não tem este livro alocado para devolução.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro inesperado: " + e.getMessage());
        }
    }

}
