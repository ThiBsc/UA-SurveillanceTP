function getXMLHttpRequest() {
    var xhr = null;
    
    if (window.XMLHttpRequest || window.ActiveXObject) {
        if (window.ActiveXObject) {
            try {
                xhr = new ActiveXObject("Msxml2.XMLHTTP");
            } catch(e) {
                xhr = new ActiveXObject("Microsoft.XMLHTTP");
            }
        } else {
            xhr = new XMLHttpRequest();
        }
    } else {
        alert("Votre navigateur ne supporte pas l'objet XMLHTTPRequest...");
        return null;
    }
    
    return xhr;
}

function executeScript(resource, callback) {
    var xhr = getXMLHttpRequest();

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0)) {
            callback(xhr.responseText);
        }
    };
    
    xhr.open("GET", resource, true);
    xhr.overrideMimeType("text/plain");
    xhr.send();
}

// afficher les etudiants de l'examen
function afficherEtudiants(contenu) {
    document.querySelector("#etudiantsliste").innerHTML=contenu;
}


// afficher 
function afficherVideo(contenu) {
    document.querySelector("#video").innerHTML=contenu;
}

function setCurTime(second) {
    var vid = document.getElementById("vid_exam");
    console.log(vid);
    vid.currentTime=second;
}
