import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import lp2g35.biblioteca.*;
import lp2g35.exc.*;

public class Inicio extends JFrame implements MouseListener {

    Color cinzaClaro = new Color(220, 220, 220);
    Color cinzaEscuro = new Color(169, 169, 169);

    private JButton botao1, botao2;

    public Inicio() {
        this.setLayout(null);

        JLayeredPane paines = new JLayeredPane();
        paines.setBounds(0, 0, 500, 450);

        Color azulPastel = new Color(173, 216, 230);
        Color brancoGelo = new Color(245, 245, 245);
        Color begeClaro = new Color(255, 239, 213);

        JPanel titulo = new JPanel();
        titulo.setBounds(50, 20, 400, 100);
        titulo.setLayout(null);
        titulo.setBackground(azulPastel);

        JLabel principal = new JLabel("• Seja Bem Vindo •", SwingConstants.CENTER);
        principal.setFont(new Font("Bradley Hand", Font.PLAIN, 44));
        principal.setBounds(0, 0, 400, 100);
        principal.setForeground(Color.BLACK);
        titulo.add(principal);

        JPanel botoes = new JPanel();
        botoes.setBounds(20, 135, 460, 250);
        botoes.setBackground(begeClaro);
        botoes.setLayout(null);

        JPanel tBotoes = new JPanel();
        tBotoes.setBounds(120, 5, 230, 27);
        tBotoes.setBackground(azulPastel);
        JLabel descricaoBotoes = new JLabel("INICIAR :", SwingConstants.CENTER);
        descricaoBotoes.setFont(new Font("Bradley Hand", Font.PLAIN, 20));
        descricaoBotoes.setForeground(Color.BLACK);
        tBotoes.add(descricaoBotoes);

        botao1 = new JButton("Sem Banco de Dados");
        botao2 = new JButton("Com Banco de Dados");

        botao1.setToolTipText("Inicia biblioteca sem carregar banco de dados");
        botao2.setToolTipText("Inicia biblioteca carregando um banco de dados especificado");

        configurarBotao(botao1, 20, 35);
        configurarBotao(botao2, 20, 135);

        botoes.add(tBotoes);
        botoes.add(botao1);
        botoes.add(botao2);

        JPanel creditos = new JPanel();
        creditos.setBounds(120, 390, 260, 27);
        creditos.setBackground(azulPastel);

        JLabel cr = new JLabel("Yuri Baena 2025.1", SwingConstants.CENTER);
        cr.setForeground(Color.BLACK);
        creditos.add(cr);

        paines.add(titulo, 0);
        paines.add(botoes, 1);
        paines.add(creditos, 2);

        this.add(paines);

        this.setTitle("Inicio");
        this.getContentPane().setBackground(brancoGelo);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(500, 450);
        this.setResizable(false);
        this.setVisible(true);
    }

    private void configurarBotao(JButton botao, int x, int y) {
        botao.setFont(new Font("Bradley Hand", Font.PLAIN, 28));
        botao.setBackground(cinzaClaro);
        botao.setForeground(Color.BLACK);
        botao.setFocusPainted(false);
        botao.setOpaque(true);
        botao.setBorderPainted(false);
        botao.setBounds(x, y, 420, 90);

        // Adiciona o efeito tátil (mouse listener)
        botao.addMouseListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton botao = (JButton) e.getSource();
            botao.setBackground(cinzaEscuro);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton botao = (JButton) e.getSource();
            botao.setBackground(cinzaClaro);

            if (botao == botao1) {
                new Principal(new Biblioteca());
                this.dispose();
            } else if (botao == botao2) {
                new Carregando();
                this.dispose();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) { }
    @Override
    public void mouseEntered(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e) { }

    public static void main(String[] args) {
        new Inicio();
    }
}
