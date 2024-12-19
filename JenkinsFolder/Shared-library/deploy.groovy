def call (String environment = dev ){
    if (environment == dev){
        echo "Deploy in dev stage"
    }
    else (environment == UAT ){
        echo "Deploy in UAT stage"
    }
}