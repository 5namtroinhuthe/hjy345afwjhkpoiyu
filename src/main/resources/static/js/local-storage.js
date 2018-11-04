
// const color2 = document.getElementsByClassName('color2');
// const color3 = document.getElementsByClassName('color3');
// const color4 = document.getElementsByClassName('color4');
// const color5 = document.getElementsByClassName('color5');
// const color6 = document.getElementsByClassName('color6');
// const color7 = document.getElementsByClassName('color7');
// const color8 = document.getElementsByClassName('color8');
// const color9 = document.getElementsByClassName('color9');
// const color10 = document.getElementsByClassName('color10');
let colors = sessionStorage.getItem('colorList') ? JSON.parse(sessionStorage.getItem('colorList')) : [];
let sizes = sessionStorage.getItem('sizeList') ? JSON.parse(sessionStorage.getItem('sizeList')) : [];
let manufacturers = sessionStorage.getItem('manufacturerList') ? JSON.parse(sessionStorage.getItem('manufacturerList')) : [];
let materials = sessionStorage.getItem('materialList') ? JSON.parse(sessionStorage.getItem('materialList')) : [];
let madeIns = sessionStorage.getItem('madeInList') ? JSON.parse(sessionStorage.getItem('madeInList')) : []



// color2.onclick= function () {
//     let color = new Predicate(colors, 'colorList');
//     let list = color.handleClick('xam', 'xamClick');
//
//     color2.href = new PreProcesssingUrl(list, sizes, manufacturers, materials, madeIns).calUrl();
// };
// color3.onclick= function () {
//     let color = new Predicate(colors, 'colorList');
//     let list = color.handleClick('den', 'denClick');
//     color3.href = new PreProcesssingUrl(list, sizes, manufacturers, materials, madeIns).calUrl();
// };
// color4.onclick= function () {
//     let color = new Predicate(colors, 'colorList');
//     let list = color.handleClick('xanh nuoc bien', 'xanhBienClick');
//     color4.href =new PreProcesssingUrl(list, sizes, manufacturers, materials, madeIns).calUrl();
// };
// color5.onclick= function () {
//     let color = new Predicate(colors, 'colorList');
//     let list = color.handleClick('do', 'doClick');
//     color5.href =new PreProcesssingUrl(list, sizes, manufacturers, materials, madeIns).calUrl();
// };
// color6.onclick= function () {
//     let color = new Predicate(colors, 'colorList');
//     let list = color.handleClick('vang', 'vangClick');
//     color6.href =new PreProcesssingUrl(list, sizes, manufacturers, materials, madeIns).calUrl();
// };
// color7.onclick= function () {
//     let color = new Predicate(colors, 'colorList');
//     let list = color.handleClick('cam', 'camClick');
//     color7.href = new PreProcesssingUrl(list, sizes, manufacturers, materials, madeIns).calUrl();
// };
// color8.onclick= function () {
//     let color = new Predicate(colors, 'colorList');
//     let list = color.handleClick('nau', 'nauClick');
//     color8.href = new PreProcesssingUrl(list, sizes, manufacturers, materials, madeIns).calUrl();
// };
// color9.onclick= function () {
//     let color = new Predicate(colors, 'colorList');
//     let list = color.handleClick('xanh la cay', 'xanhCayClick');
//     color9.href = new PreProcesssingUrl(list, sizes, manufacturers, materials, madeIns).calUrl();
// };
// color10.onclick= function () {
//     let color = new Predicate(colors, 'colorList');
//     let list = color.handleClick('tim', 'timClick');
//     color10.href = new PreProcesssingUrl(list, sizes, manufacturers, materials, madeIns).calUrl();
// };

class PreProcesssingUrl {
    constructor(colorsList, sizesList, manufacturersList, materialsList, madeInsList) {
        this.colorsList = colorsList;
        this.sizesList = sizesList;
        this.manufacturersList = manufacturersList;
        this.materialsList = materialsList;
        this.madeInsList = madeInsList;
    }

    calUrl() {
        let href = '/product/get?';
        if (this.colorsList.length > 0) {
            let tmp = '';
            this.colorsList.forEach(function (item, index, array) {
                tmp = tmp + (item + '%');
            });
            tmp = 'color=' + tmp;
            if (!href.endsWith('?')) href = href + '&' + tmp;
            else href = href + tmp;
        }
        // if (this.sizesList.length > 0) {
        //     this.sizesList.forEach(function (item,index,array) {
        //         tmp =item+'%';
        //     });
        //     let tmp = 'size=' + tmp;
        //     if (!href.endsWith('?')) href = href + '&' + tmp;
        //     else href = href + tmp;
        // }
        // if (this.materialsList.length > 0) {
        //     this.materialsList.forEach(function (item,index,array){
        //         tmp =item+'%';
        //     });
        //     let tmp = 'materials=' + tmp;
        //     if (!href.endsWith('?')) href = href + '&' + tmp;
        //     else href = href + tmp;
        // }
        // if (this.manufacturersList.length > 0) {
        //     this.manufacturersList.forEach(function (item,index,array){
        //         tmp +=item+'%';
        //     });
        //     let tmp = 'manufacturer=' + tmp;
        //     if (!href.endsWith('?')) href = href + '&' + tmp;
        //     else href = href + tmp;
        // }
        // if (this.madeInsList.length > 0) {
        //     this.madeInsList.forEach(function (item,index,array){
        //         tmp =item+'%';
        //     });
        //    let tmp = 'madeIn=' + tmp;
        //     if (!href.endsWith('?')) href = href + '&' + tmp;
        //     else href = href + tmp;
        // }
        return href;
    }

}

class Predicate {
    constructor(list, keyPredicate) {
        this.list = list;
        this.keyPredicate = keyPredicate;
    }

    handleClick(colorName, clickCount) {
        let clickNumber = 0;
        if (sessionStorage.getItem(clickCount)) {
            clickNumber = Number(sessionStorage.getItem(clickCount)) + 1;
        }
        else {
            clickNumber += 1;
        }
        sessionStorage.setItem(clickCount, JSON.stringify(clickNumber));
        if (clickNumber / 2 != 0) {
            this.list.push(colorName);
        }
        else {
            if (this.list.length > 0) this.list.slice(this.list.indexOf(colorName), 1);
            else sessionStorage.removeItem(this.keyPredicate);
        }
        sessionStorage.setItem(this.keyPredicate, JSON.stringify(this.list));
        return this.list;
    }
}







