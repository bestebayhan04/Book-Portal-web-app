import React,{useEffect} from "react";
import Slider from "../components/Slider";

import "../comp_css/Slider.css"



const Home = () => {
    const veritycard = [

    ];


    const styleFixedImg = {
        width: "100%",
        height: "25vh",
        marginTop: "10px",
        marginBottom: "10px",
    };
    useEffect(() => {
        document.title = 'Ecommerse | Home Page';
        return () => {
            document.title = 'Ecommerse App';
        };
    }, []);

    return (
        <>

            <div className="ImageFixed">
                <img
                    style={styleFixedImg}
                    src="https://m.media-amazon.com/images/S/aplus-media-library-service-media/e7e7edb5-1cc6-4ef4-910f-d4a540cc07cc.__CR0,0,1464,600_PT0_SX1464_V1___.jpg?im=Resize=(680,420)"
                    alt="Image"
                />

                <img
                    style={styleFixedImg}
                    src="https://aws-obg-image-lb-4.tcl.com/content/dam/brandsite/region/in/blog/pc/detail/blog-april/best-fully-automatic-washing-machines/pc.jpg?im=Resize=(1240,150)"
                    alt="Image"
                />
            </div>


        </>
    );
};

export default Home;
