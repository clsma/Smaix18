/* 
 *Author     : Cl-sma(Carlos Pinto)
 */


function CHARACTER() {
    this.rep = function () {
        var r = this.toLowerCase();
        r = r.replace(new RegExp("\\s", 'g'), "_");
        r = r.replace(new RegExp("[äã]", 'g'), "a");
        r = r.replace(new RegExp("æ", 'g'), "ae");
        r = r.replace(new RegExp("ç", 'g'), "c");
        r = r.replace(new RegExp("[ĕë]", 'g'), "e");
        r = r.replace(new RegExp("[ïĭ]", 'g'), "i");
        r = r.replace(new RegExp("ň", 'g'), "n");
        r = r.replace(new RegExp("[öő]", 'g'), "o");
        r = r.replace(new RegExp("�", 'g'), "oe");
        r = r.replace(new RegExp("[ü]", 'g'), "u");
        r = r.replace(new RegExp("[űü]", 'g'), "y");
        r = r.replace(/[á]/gi, 'a')
                .replace(/[é]/gi, 'e')
                .replace(/[í]/gi, 'i')
                .replace(/[ó]/gi, 'o')
                .replace(/[ú]/gi, 'u');
        return r;
    }
}

function TESTER() {
    this.NUMBER = /^[0-9,.]*$/,
            this.DECIMAL = /^[0-9,.]*$/,
            this.LETRANUMERO = /^[aA-zZ,.0-9ñÑ()-\s]|[áéíóúÁÉÍÓÚ]$/,
            this.LETRA = /['A-Z.,ñÑ\s]|[ÁÉÍÓÚ]/i,
            this.EMAIL = /^[aA-zZ,.0-9ñÑ()-\s]|[áéíóúÁÉÍÓÚ]|[#+@_]$/,
            this.ADDRESS = /^[aA-zZ,.0-9ñÑ()\-\s]|[áéíóúÁÉÍÓÚ]|[#+]$/,
            this.WEB = /^[aA-zZ,.0-9ñÑ\?\-\s\&]|[áéíóúÁÉÍÓÚ]|[#\=_@\/:]$/,
            this.PASSWORD = /^[aA-zZ]|[0-9]|[\#\-\+\_\@]$/g,
            this.CHARACTERS = /^[aA-zZ]|[ÁÉÍÓÚáéíóú]|[\´.,;:]|[0-9]|[\#\-\+\_\@\?\/\%\(\)\*\"\&]|[\ñ\Ñ\s]$/g,
            this.DNI = /^[0-9]$/g;
}
