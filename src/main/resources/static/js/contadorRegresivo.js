// Función para manejar el contador regresivo
function iniciarContadorRegresivo(urlLogout) {
	
	var tiempoExpiracion = 300;
    var tiempoExpiracionMillis = tiempoExpiracion * 1000; // Tiempo en milisegundos
    tiempoExpiracionMillis += new Date().getTime();

    function actualizarContadorRegresivo() {
        var tiempoActualMillis = new Date().getTime(); // Obtenmos el tiempo actual en milisegundos
        var tiempoRestante = tiempoExpiracionMillis - tiempoActualMillis; // Calculamos el tiempo restante
        var segundosRestantes = Math.floor(tiempoRestante / 1000); // Conviertimos el tiempo restante en segundos
		
        //console.log("Tiempo restante: " + segundosRestantes + " segundos");

        if (segundosRestantes <= 0) {
            clearInterval(intervalId); // Detenemos el contador
            console.log("Tiempo restante detenido");

            
            var datos = {};
            fetch(urlLogout, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(datos),
            })
            .then((response) => response.json())
            .then((data) => {
                var message = data.message;
                console.log(message);
                
                // Redirige a la vista de login
                window.location.href = '/LoginSite'; 
            })
            .catch((error) => {
                console.error("Error al registrar el log:", error);
            });
        }
    }

	// Agregamos un evento si llega haber un click en el documento
    document.addEventListener("click", function () {
		console.log("Reporte de actividad");
		
		     var datos = {};
            fetch("./ReporteActividad", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(datos),
            })
            .then((response) => response.json())
            .then((data) => {
                var message = data.message;
                console.log(message);
                
   
            })
            .catch((error) => {
                console.error("Error al registrar el log:", error);
            });
            
        tiempoExpiracionMillis = tiempoExpiracion * 1000;
        tiempoExpiracionMillis += new Date().getTime();
        
        actualizarContadorRegresivo();
        
    });

	// Llamamos la función para actualizar el contador regresivo al cargar la página
    var intervalId = setInterval(actualizarContadorRegresivo, 1000);
    actualizarContadorRegresivo();
}