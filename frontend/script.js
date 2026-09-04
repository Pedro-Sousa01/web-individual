const API_URL = 'http://localhost:8080/midias';

window.onload = function() {
    carregarMidias();
    carregarCategorias();
};

function validarTitulo() {
    const titulo = document.getElementById("titulo");
    const erroTitulo = document.getElementById("erroTitulo");
    if (titulo.value.trim() === "") {
        erroTitulo.textContent = "❌ O título não pode estar vazio.";
        erroTitulo.className = "mensagem erro";
        return false;
    } else {
        erroTitulo.textContent = "✓ Título válido.";
        erroTitulo.className = "mensagem sucesso";
        return true;
    }
}

function validarTipo() {
    const tipo = document.getElementById("tipo");
    const erroTipo = document.getElementById("erroTipo");
    if (tipo.value === "") {
        erroTipo.textContent = "❌ Selecione uma categoria.";
        erroTipo.className = "mensagem erro";
        return false;
    } else {
        erroTipo.textContent = "✓ Categoria selecionada.";
        erroTipo.className = "mensagem sucesso";
        return true;
    }
}

function validarNota() {
    const nota = document.getElementById("nota");
    const erroNota = document.getElementById("erroNota");
    const valor = parseInt(nota.value);
    
    if (nota.value.trim() === "") {
        erroNota.textContent = "❌ A nota não pode estar vazia.";
        erroNota.className = "mensagem erro";
        return false;
    } else if (valor < 1 || valor > 10) {
        erroNota.textContent = "❌ A nota deve ser entre 1 e 10.";
        erroNota.className = "mensagem erro";
        return false;
    } else {
        erroNota.textContent = "✓ Nota válida.";
        erroNota.className = "mensagem sucesso";
        return true;
    }
}

function validarFormulario() {
    const tituloValido = validarTitulo();
    const tipoValido = validarTipo();
    const notaValida = validarNota();
    return tituloValido && tipoValido && notaValida;
}

function cadastrarMidia() {
    const erroBotao = document.getElementById("erroBotao");
    
    if (validarFormulario()) {
        const novaMidia = {
            titulo: document.getElementById('titulo').value,
            tipo: document.getElementById('tipo').value,
            nota: document.getElementById('nota').value,
            foto_url: document.getElementById('foto').value,
            comentario: document.getElementById('comentario').value
        };

        fetch(API_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(novaMidia)
        })
        .then(response => {
            if(!response.ok) throw new Error("Erro na rede");
            return response.json();
        })
        .then(() => {
            erroBotao.textContent = "✓ Mídia adicionada com sucesso!";
            erroBotao.className = "mensagem mensagemButton sucesso";
            
            document.getElementById('titulo').value = '';
            document.getElementById('tipo').value = '';
            document.getElementById('nota').value = '';
            document.getElementById('foto').value = '';
            document.getElementById('comentario').value = '';
            
            document.querySelectorAll('.mensagem:not(.mensagemButton)').forEach(el => el.textContent = '');

            carregarMidias();
        })
        .catch(error => {
            erroBotao.textContent = "❌ Erro ao conectar com o servidor Spring Boot.";
            erroBotao.className = "mensagem mensagemButton erro";
        });

    } else {
        erroBotao.textContent = "❌ Preencha os campos obrigatórios corretamente.";
        erroBotao.className = "mensagem mensagemButton erro";
    }
}

function carregarCategorias() {
    fetch(`${API_URL}/tipos`)
    .then(response => response.json())
    .then(tipos => {
        const select = document.getElementById('tipo');
        if(tipos.length > 0) {
            for(i = 0; i < tipos.length; i++) {
                select.add(new Option(tipos[i], i));
            }
        }
    })
}

function carregarMidias() {
    fetch(API_URL)
    .then(response => response.json())
    .then(midias => {
        const listContainer = document.getElementById('mediaList');
        if(midias.length > 0) {
            listContainer.innerHTML = ''; 
            
            midias.forEach(midia => {
                if(midia.foto_url == '' || midia.foto_url == undefined) {
                    midia.foto_url = "https://www.marse.com.br/application/views/images/naodisponivel.png";
                }
                
                const html = `
                    <div class="media-card">
                        <img src="${midia.foto_url}" alt="Capa de ${midia.titulo}">
                        <div class="media-info">
                            <h3>${midia.titulo} <span class="tag">${midia.tipo}</span></h3>
                            <div class="nota">⭐ ${midia.nota}/10</div>
                            <p class="comentario">${midia.comentario ? `"${midia.comentario}"` : 'Sem comentário'}</p>
                        </div>
                    </div>
                `;
                listContainer.innerHTML += html;
            });
        }
    })
    .catch(error => console.error(error));
}