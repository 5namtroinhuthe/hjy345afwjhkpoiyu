li = document.getElementsByClassName('color-product');
const color1 = document.getElementsByClassName('color1');
const color2 = document.getElementsByClassName('color2');
const color3 = document.getElementsByClassName('color3');
const color4 = document.getElementsByClassName('color4');
const color5 = document.getElementsByClassName('color5');
const color6 = document.getElementsByClassName('color6');
const color7 = document.getElementsByClassName('color7');
const color8 = document.getElementsByClassName('color8');
const color9 = document.getElementsByClassName('color9');
const color10 = document.getElementsByClassName('color10');
let colors = localStorage.getItem('colorList') ? JSON.parse(localStorage.getItem('colorList')) : [];
let sizes = localStorage.getItem('sizeList') ? JSON.parse(localStorage.getItem('sizeList')) : [];
let manufacturers = localStorage.getItem('manufacturerList') ? JSON.parse(localStorage.getItem('manufacturerList')) : [];
let materials = localStorage.getItem('materialList') ? JSON.parse(localStorage.getItem('materialList')) : [];
let madeIns = localStorage.getItem('madeInList') ? JSON.parse(localStorage.getItem('madeInList')) : []
// localStorage.setItem('colorList', JSON.stringify(itemsArray));

// const data = JSON.parse(localStorage.getItem('colorList'));
color1.addEventListener('click', function () {
    let color = new Predicate(colors, 'colorList');
    color.handleClick('trang', 'trangClick');
});
color2.addEventListener('click', function () {
    let color = new Predicate(colors, 'colorList');
    color.handleClick('xam', 'xamClick');
});
color3.addEventListener('click', function () {
    let color = new Predicate(colors, 'colorList');
    color.handleClick('den', 'denClick');
});
color4.addEventListener('click', function () {
    let color = new Predicate(colors, 'colorList');
    color.handleClick('xanh nuoc bien', 'xanhBienClick');
});
color5.addEventListener('click', function () {
    let color = new Predicate(colors, 'colorList');
    color.handleClick('do', 'doClick');
});
color6.addEventListener('click', function () {
    let color = new Predicate(colors, 'colorList');
    color.handleClick('vang', 'vangClick');
});
color7.addEventListener('click', function () {
    let color = new Predicate(colors, 'colorList');
    let list=color.handleClick('cam', 'camClick');
    color7.href=new PreProcesssingUrl(list,sizes,manufacturers,materials,madeIns).calUrl();
});
color8.addEventListener('click', function () {
    let color = new Predicate(colors, 'colorList');
    color.handleClick('nau', 'nauClick');
});
color9.addEventListener('click', function () {
    let color = new Predicate(colors, 'colorList');
    color.handleClick('xanh la cay', 'xanhCayClick');
});
color10.addEventListener('click', function () {
    let color = new Predicate(colors, 'colorList');
    color.handleClick('tim', 'timClick');
});

class PreProcesssingUrl {
    colors = [];
    sizes = [];
    manufacturers = [];
    materials = [];
    madeIns = [];


    constructor(colors, sizes, manufacturers, materials, madeIns) {
        this.colors = colors;
        this.sizes = sizes;
        this.manufacturers = manufacturers;
        this.materials = materials;
        this.madeIns = madeIns;
    }

    calUrl() {
        let href = '/product/get?';
        let tmp = '';
        if (this.colors.size() > 0) {
            for (let i = 0; i < this.colors.size(); i++) {
                tmp = this.colors[i] + '%';
            }
            tmp='color='+tmp;
            href=href+tmp;
        }
        if (this.sizes > 0) {
            for (let i = 0; i < this.sizes.size(); i++) {
                tmp = this.sizes[i] + '%';
            }
            tmp='size='+tmp;
            href=href+tmp;
        }
        if (this.manufacturers > 0) {
            for (let i = 0; i < this.manufacturers.size(); i++) {
                tmp = this.manufacturers[i] + '%';
            }
            tmp='manufacturer='+tmp;
            href=href+tmp;
        }
        if (this.materials > 0) {
            for (let i = 0; i < this.materials.size(); i++) {
                tmp = this.materials[i] + '%';
            }
            tmp='materials='+tmp;
            href=href+tmp;
        }
        if (this.madeIns > 0) {
            for (let i = 0; i < this.madeIns.size(); i++) {
                tmp = this.madeIns[i] + '%';
            }
            tmp='madeIn='+tmp;
            href=href+tmp;
        }
        return href;
    }

}

class Predicate {
    list = [];
    keyPredicate = '';

    constructor(list, keyPredicate) {
        this.list = list;
        this.keyPredicate = keyPredicate;
    }


    getList() {
        return this.list;
    }

    handleClick(colorName, clickCount) {
        let clickNumber = 0;
        if (localStorage.getItem(clickCount)) {
            clickNumber = Number(localStorage.getItem(clickCount)) + 1;
        }
        else {
            clickNumber += 1;
        }
        localStorage.setItem(clickCount, JSON.stringify(clickNumber));
        if (clickNumber / 2 == 0) {
            if (this.list.size() > 0) this.list.slice(this.list.indexOf(colorName), 1);
            else localStorage.removeItem(this.keyPredicate);
        }
        else {
            this.list.push(colorName);
        }
        localStorage.setItem(this.keyPredicate, JSON.stringify(this.list));
        return this.list;
    }
}







