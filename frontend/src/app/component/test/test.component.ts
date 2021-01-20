import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {TokenService} from '../../service/token.service';
import {PathConstants} from '../../helper/constants/path';

@Component({
    selector: 'app-test',
    templateUrl: './test.component.html',
    styleUrls: ['./test.component.css']
})
export class TestComponent implements OnInit {
    constructor(private router: Router) {
    }

    ngOnInit(): void {
    }

    onLogout(): void {
        TokenService.clearStorage();
        this.router.navigate([PathConstants.SIGN_IN]).then();
    }

}
