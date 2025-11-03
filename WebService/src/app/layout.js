// src/app/layout.js
import "./globals.css";
import Header from "@/components/Header";

export const metadata = {
  title: "Vagas Online",
  description: "Website para listar vagas de emprego",
  icons: {
    icon: '/favicon.ico'
  },
};

export default function RootLayout({ children }) {
  return (
    <html lang="pt-BR">
      <body>
        <Header />
        <main className="main-container">
          {children}
        </main>
      </body>
    </html>
  );
}