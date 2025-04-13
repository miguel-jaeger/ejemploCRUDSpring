<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Listado de Usuarios</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
  <div class="container mt-5">
    <h1 class="mb-4">Usuarios</h1>
    <button class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#usuarioModal" onclick="abrirModalNuevo()">Nuevo Usuario</button>

    <table class="table table-striped">
      <thead class="table-dark">
        <tr>
          <th>#</th>
          <th>Nombre</th>
          <th>Apellido</th>
          <th>Correo</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>
        <?php
          $jsonUsuarios = file_get_contents('http://localhost:4001/api/usuarios');
          $usuarios = json_decode($jsonUsuarios, true);

          if ($usuarios && is_array($usuarios)) {
              foreach ($usuarios as $index => $usuario) {
                  echo "<tr>";
                  echo "<td>" . ($index + 1) . "</td>";
                  echo "<td>" . htmlspecialchars($usuario['nombre']) . "</td>";
                  echo "<td>" . htmlspecialchars($usuario['apellido']) . "</td>";
                  echo "<td>" . htmlspecialchars($usuario['correo']) . "</td>";
                  echo "<td>
                          <button class='btn btn-warning btn-sm me-1' onclick='editarUsuario(" . htmlspecialchars(json_encode($usuario)) . ")'>Editar</button>
                          <button class='btn btn-danger btn-sm' onclick='eliminarUsuario(" . $usuario['idPersona'] . ")'>Eliminar</button>
                        </td>";
                  echo "</tr>";
              }
          } else {
              echo "<tr><td colspan='5'>No se encontraron usuarios.</td></tr>";
          }
        ?>
      </tbody>
    </table>
  </div>

  <!-- Modal -->
  <div class="modal fade" id="usuarioModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <form id="formUsuario">
          <div class="modal-header">
            <h5 class="modal-title" id="modalTitle">Usuario</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
          </div>
          <div class="modal-body">
            <input type="hidden" id="idPersona" name="idPersona">
            <div class="mb-3">
              <label for="nombre" class="form-label">Nombre</label>
              <input type="text" class="form-control" id="nombre" name="nombre" required>
            </div>
            <div class="mb-3">
              <label for="apellido" class="form-label">Apellido</label>
              <input type="text" class="form-control" id="apellido" name="apellido" required>
            </div>
            <div class="mb-3">
              <label for="correo" class="form-label">Correo</label>
              <input type="email" class="form-control" id="correo" name="correo" required>
            </div>
            <div class="mb-3">
              <label for="contrasena" class="form-label">Contraseña</label>
              <input type="password" class="form-control" id="contrasena" name="contrasena">
              <small class="form-text text-muted" id="passwordHint"></small>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
            <button type="submit" class="btn btn-primary" id="btnSave">Guardar</button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  <script>
    let usuarioModal;
    
    document.addEventListener('DOMContentLoaded', function() {
      // Inicializar el modal correctamente
      usuarioModal = new bootstrap.Modal(document.getElementById('usuarioModal'));
      
      // Configurar el evento submit del formulario
      document.getElementById('formUsuario').addEventListener('submit', guardarUsuario);
    });

    function abrirModalNuevo() {
      document.getElementById('formUsuario').reset();
      document.getElementById('idPersona').value = '';
      document.getElementById('modalTitle').textContent = 'Nuevo Usuario';
      document.getElementById('contrasena').required = true;
      document.getElementById('passwordHint').textContent = 'La contraseña es obligatoria para nuevos usuarios';
    }

    function editarUsuario(usuario) {
      try {
        // Si usuario es string (lo que podría pasar por el JSON.stringify en PHP), parsearlo
        if (typeof usuario === 'string') {
          usuario = JSON.parse(usuario);
        }
        
        document.getElementById('idPersona').value = usuario.idPersona;
        document.getElementById('nombre').value = usuario.nombre || '';
        document.getElementById('apellido').value = usuario.apellido || '';
        document.getElementById('correo').value = usuario.correo || '';
        document.getElementById('contrasena').value = '';
        document.getElementById('contrasena').required = false;
        document.getElementById('modalTitle').textContent = 'Editar Usuario';
        document.getElementById('passwordHint').textContent = 'Dejar en blanco para mantener la contraseña actual';
        
        usuarioModal.show();
      } catch (error) {
        console.error('Error al procesar datos de usuario:', error);
        alert('Error al cargar datos del usuario');
      }
    }

    function guardarUsuario(e) {
      e.preventDefault();
      
      const form = e.target;
      const idPersona = form.idPersona.value.trim();
      
      // Preparar los datos del formulario
      const data = {
        nombre: form.nombre.value.trim(),
        apellido: form.apellido.value.trim(),
        correo: form.correo.value.trim()
      };
      
      // Añadir contraseña solo si se ha proporcionado
      if (form.contrasena.value) {
        data.contrasena = form.contrasena.value;
      }
      
      // Si es edición, añadir el ID
      if (idPersona) {
        data.idPersona = parseInt(idPersona);
      }
      
      // Enviar la solicitud al servidor
      fetch('http://localhost:4001/api/usuarios', {
        method: idPersona ? 'PUT' : 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
      })
      .then(res => {
        if (res.ok) {
          usuarioModal.hide();
          location.reload();
        } else {
          return res.text().then(text => {
            throw new Error(text || 'Error al procesar la solicitud');
          });
        }
      })
      .catch(err => {
        alert("Error: " + (err.message || 'Error en la conexión al servidor'));
        console.error(err);
      });
    }

    function eliminarUsuario(id) {
      if (!id) {
        alert("ID de usuario no válido");
        return;
      }
      
      if (confirm("¿Estás seguro de eliminar este usuario?")) {
        fetch('http://localhost:4001/api/usuarios', {
          method: 'DELETE',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({ idPersona: id })
        })
        .then(res => {
          if (res.ok) {
            location.reload();
          } else {
            return res.text().then(text => {
              throw new Error(text || 'Error al eliminar');
            });
          }
        })
        .catch(err => {
          alert("Error: " + (err.message || 'Error al conectar con el servidor'));
          console.error(err);
        });
      }
    }
  </script>
</body>
</html>