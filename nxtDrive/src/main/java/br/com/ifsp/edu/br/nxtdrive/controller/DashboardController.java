package br.com.ifsp.edu.br.nxtdrive.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.ifsp.edu.br.nxtdrive.model.Categoria;
import br.com.ifsp.edu.br.nxtdrive.model.Produto;
import br.com.ifsp.edu.br.nxtdrive.model.Usuario;
import br.com.ifsp.edu.br.nxtdrive.repository.UsuarioRepository;
import br.com.ifsp.edu.br.nxtdrive.service.CategoriaService;
import br.com.ifsp.edu.br.nxtdrive.service.ProdutoService;

@Controller
public class DashboardController {

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	// =========================
	// PAINEL ADMINISTRATIVO
	// =========================

	// Qualquer usuário autenticado pode visualizar
	@GetMapping("/dashboard")
	public String getDash(Model model) {

		model.addAttribute("categorias", categoriaService.listarCategorias());
		model.addAttribute("produtos", produtoService.listarProdutos());

		return "dashboard";
	}

	// =========================
	// HOME DA LOJA
	// =========================

	@GetMapping("/home")
	public String home(Model model, java.security.Principal principal) {

		if (principal != null) {
			// Busca o usuário logado pelo e-mail e passa o nome
			usuarioRepository.findByEmail(principal.getName()).ifPresent(usuario -> {
				model.addAttribute("nomeUsuario", usuario.getNome());
			});
		}

		model.addAttribute("produtos", produtoService.listarProdutos());
		model.addAttribute("listaCategorias", categoriaService.listarCategorias());

		return "home";
	}

	// =========================
	// IMAGEM DO PRODUTO
	// =========================

	@GetMapping("/produto/imagem/{id}")
	@ResponseBody
	public byte[] imagemProduto(@PathVariable UUID id) {

		Produto produto = produtoService.buscarPorId(id);

		return produto.getFoto();
	}

	// =========================
	// ACESSO NEGADO
	// =========================

	@GetMapping("/403")
	public String acessoNegado() {
		return "403";
	}

	// =========================
	// CATEGORIAS
	// =========================

	@PostMapping("/adicionarCategoria")
	@PreAuthorize("hasRole('ADMIN')")
	public String adicionarCategoria(@ModelAttribute Categoria categoria, Model model) {

		if (!categoriaService.adicionarCategoria(categoria)) {

			model.addAttribute("erro", "Já existe uma categoria com esse nome.");

			model.addAttribute("categorias", categoriaService.listarCategorias());

			model.addAttribute("produtos", produtoService.listarProdutos());

			return "dashboard";
		}

		return "redirect:/dashboard";
	}

	@GetMapping("/listarCategorias")
	public String listarCategorias(Model model) {

		model.addAttribute("categorias", categoriaService.listarCategorias());

		model.addAttribute("produtos", produtoService.listarProdutos());

		return "dashboard";
	}

	@PostMapping("/editarCategoria")
	@PreAuthorize("hasRole('ADMIN')")
	public String editarCategoria(@ModelAttribute Categoria categoria) {

		categoriaService.editarCategoria(categoria);

		return "redirect:/dashboard";
	}

	@PostMapping("/removerCategoria")
	@PreAuthorize("hasRole('ADMIN')")
	public String removerCategoria(@ModelAttribute Categoria categoria) {

		categoriaService.excluirCategoria(categoria.getIdCategoria());

		return "redirect:/dashboard";
	}

	@GetMapping("/usuarios")
	public String listarUsuarios(Model model) {
		model.addAttribute("contexto", "usuarios");
		model.addAttribute("usuarios", usuarioRepository.findAll());
		return "dashboard";
	}

	@PostMapping("/usuarios/excluir/{id}")
	public String excluirUsuario(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
		try {
			usuarioRepository.deleteById(id);
			redirectAttributes.addFlashAttribute("sucesso", "Usuário removido do sistema!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("erro", "Não foi possível excluir o usuário.");
		}
		return "redirect:/usuarios";
	}

	@PostMapping("/usuarios/alterar-role/{id}")
	public String alterarRole(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
		Usuario usuario = usuarioRepository.findById(id).orElse(null);
		if (usuario != null) {
			// Alterna entre ADMIN e USER
			if (usuario.getAcesso() == Usuario.Role.ADMIN) {
				usuario.setAcesso(Usuario.Role.USER);
			} else {
				usuario.setAcesso(Usuario.Role.ADMIN);
			}
			usuarioRepository.save(usuario);
			redirectAttributes.addFlashAttribute("sucesso", "Permissão atualizada com sucesso!");
		}
		return "redirect:/usuarios";
	}

}